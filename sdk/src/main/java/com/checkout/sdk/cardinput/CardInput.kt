package com.checkout.sdk.cardinput

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.view.View.OnFocusChangeListener
import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.architecture.PresenterStore
import com.checkout.sdk.store.DataStore
import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.utils.AfterTextChangedListener
import com.checkout.sdk.utils.CardUtils

/**
 * <h1>CardInput class</h1>
 * The CardInput class has the purpose extending an AppCompatEditText and provide validation
 * and formatting for the user's card details.
 *
 *
 * This class will validate on the "afterTextChanged" event and display a card icon on the right
 * side based on  the users input. It will also span spaces following the [CardUtils] details.
 */
class CardInput @JvmOverloads constructor(
    internal var mContext: Context,
    attrs: AttributeSet? = null
) : android.support.v7.widget.AppCompatEditText(mContext, attrs),
    MvpView<CardInputUiState> {


    private val inMemoryStore = InMemoryStore.Factory.get()
    private var mCardInputListener: Listener? = null
    private lateinit var presenter: CardInputPresenter

    /**
     * The UI initialisation
     *
     *
     * Used to initialise element as well as setting up appropriate listeners
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        // Create/get and start the presenter
        presenter = PresenterStore.getOrCreate(
            CardInputPresenter::class.java,
            { CardInputPresenter() })

        presenter.start(this)

        // Add listener for text input
        addTextChangedListener(object : AfterTextChangedListener() {
            override fun afterTextChanged(text: Editable) {
                val cardInputUseCase = CardInputUseCase(text, DataStore.getInstance(), inMemoryStore)
                presenter.textChanged(cardInputUseCase)
            }
        })

        // Add listener for focus

        // When the CardInput loses focus check if the card number is not valid and trigger an error
        onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            val cardFocusUseCase = CardFocusUseCase(hasFocus, DataStore.getInstance())
            presenter.focusChanged(cardFocusUseCase)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.stop()
    }

    override fun onStateUpdated(uiState: CardInputUiState) {
        // Get Card type
        filters =
                arrayOf<InputFilter>(InputFilter.LengthFilter(uiState.cardType.maxCardLength))
        // Set the CardInput icon based on the type of card
        setCardTypeIcon(uiState.cardType)

        restoreCardNumberIfNecessary(uiState)

        showOrClearErrors(uiState)
    }

    /**
     * This method will display a card icon associated to the specific card scheme
     */
    private fun setCardTypeIcon(type: CardUtils.Cards) {
        val img: Drawable
        if (type.resourceId != 0) {
            img = context.resources.getDrawable(type.resourceId)
            img.setBounds(0, 0, 68, 68)
            setCompoundDrawables(null, null, img, null)
            compoundDrawablePadding = 5
        } else {
            setCompoundDrawables(null, null, null, null)
        }
    }

    private fun restoreCardNumberIfNecessary(cardInputResult: CardInputUiState) {
        if (text.isEmpty() && cardInputResult.cardNumber.isNotEmpty()) {
            setText(cardInputResult.cardNumber)
            setSelection(cardInputResult.cardNumber.length)
            val cardInputUseCase = CardInputUseCase(text, DataStore.getInstance(), inMemoryStore)
            presenter.textChanged(cardInputUseCase)
        }
    }

    private fun showOrClearErrors(cardInputResult: CardInputUiState) {
        mCardInputListener?.let {
            if (cardInputResult.showCardError) {
                it.onCardError()
            } else {
                it.onClearCardError()
            }

            if (cardInputResult.inputFinished) {
                it.onCardInputFinish(cardInputResult.cardNumber)
            }
        }
    }

    /**
     * Used to set the callback listener for when the card input is completed
     */
    fun setCardListener(listener: Listener) {
        this.mCardInputListener = listener
    }

    /**
     * Clear all the ui and backing values
     */
    fun clear() {
        text = SpannableStringBuilder("")
    }

    /**
     * An interface needed to communicate with the parent once the field is successfully completed
     */
    interface Listener {
        fun onCardInputFinish(number: String)

        fun onCardError()

        fun onClearCardError()
    }
}
