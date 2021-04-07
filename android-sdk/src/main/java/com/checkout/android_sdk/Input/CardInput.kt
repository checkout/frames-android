package com.checkout.android_sdk.Input

import android.content.Context
import android.content.res.Resources
import android.graphics.Rect
import android.text.Editable
import android.text.InputFilter
import android.text.SpannableStringBuilder
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
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
    context: Context,
    attrs: AttributeSet? = null
) : AppCompatEditText(context, attrs), CardInputPresenter.CardInputView {

    private var mCardInputListener: Listener? = null
    private lateinit var mPresenter: CardInputPresenter
    private var mAfterTextChangedListener: AfterTextChangedListener? = null

    /**
     * The UI initialisation
     *
     * Used to initialise element as well as setting up appropriate listeners
     */
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        // Create/get and start the presenter
        mPresenter = PresenterStore.getOrCreate(CardInputPresenter::class.java) {
            CardInputPresenter(DataStore.getInstance())
        }

        mPresenter.start(this)

        // Add listener for text input
        val textChangedListener = object : AfterTextChangedListener() {
            override fun afterTextChanged(text: Editable) {
                mPresenter.textChanged(text)
            }
        }
        mAfterTextChangedListener = textChangedListener
        addTextChangedListener(textChangedListener)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mPresenter.stop(this)
        mAfterTextChangedListener?.let {
            removeTextChangedListener(it)
        }
        mAfterTextChangedListener = null
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        // When the CardInput loses focus check if the card number is not valid and trigger an error
        mPresenter.focusChanged(focused)
    }

    override fun onStateUpdated(uiState: CardInputPresenter.CardInputUiState) {
        // Set length filter based on card type
        filters = filters
            .filter {
                it !is InputFilter.LengthFilter
            }
            .plus(InputFilter.LengthFilter(uiState.cardType.maxCardLength))
            .toTypedArray()

        // Set the CardInput icon based on the type of card
        setCardTypeIcon(uiState.cardType)

        restoreCardNumberIfNecessary(uiState)

        showOrClearErrors(uiState)
    }

    /**
     * This method will display a card icon associated to the specific card scheme
     */
    private fun setCardTypeIcon(type: CardUtils.Cards) {
        val img = if (type.resourceId > 0) {
            try {
                ContextCompat.getDrawable(context, type.resourceId)
            } catch (e: Resources.NotFoundException) {
                // Resource not found.
                null
            }
        } else null

        if (img != null) {
            img.setBounds(0, 0, 68, 68)
            setCompoundDrawables(null, null, img, null)
            compoundDrawablePadding = 5
        } else {
            setCompoundDrawables(null, null, null, null)
        }
    }

    private fun restoreCardNumberIfNecessary(cardInputResult: CardInputPresenter.CardInputUiState) {
        if (text?.isEmpty() == true && cardInputResult.cardNumber.isNotEmpty()) {
            setText(cardInputResult.cardNumber, BufferType.EDITABLE)
            setSelection(cardInputResult.cardNumber.length)
            mPresenter.textChanged(editableText)
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
