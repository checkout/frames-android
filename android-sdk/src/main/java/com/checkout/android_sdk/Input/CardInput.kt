package com.checkout.android_sdk.Input

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import android.view.View.OnFocusChangeListener
import com.checkout.android_sdk.Architecture.PresenterStore
import com.checkout.android_sdk.Presenter.CardInputPresenter
import com.checkout.android_sdk.Store.DataStore
import com.checkout.android_sdk.Utils.AfterTextChangedListener
import com.checkout.android_sdk.Utils.CardUtils

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
) : android.support.v7.widget.AppCompatEditText(mContext, attrs), CardInputPresenter.CardInputView {


    private var mCardInputListener: CardInput.Listener? = null
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
            { CardInputPresenter(DataStore.getInstance()) })

        presenter.start(this)

        // Add listener for text input
        addTextChangedListener(object : AfterTextChangedListener() {
            override fun afterTextChanged(text: Editable) {
                presenter.textChanged(text)
            }
        })

        // Add listener for focus

        // When the CardInput loses focus check if the card number is not valid and trigger an error
        onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            presenter.focusChanged(hasFocus)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.stop()
    }

    override fun onStateUpdated(uiState: CardInputPresenter.CardInputUiState) {
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

    private fun restoreCardNumberIfNecessary(cardInputResult: CardInputPresenter.CardInputUiState) {
        if (text.isEmpty() && cardInputResult.cardNumber.isNotEmpty()) {
            setText(cardInputResult.cardNumber)
            setSelection(cardInputResult.cardNumber.length)
            presenter.textChanged(text)
        }
    }

    private fun showOrClearErrors(cardInputResult: CardInputPresenter.CardInputUiState) {
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
