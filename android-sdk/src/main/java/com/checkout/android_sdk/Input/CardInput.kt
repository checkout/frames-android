package com.checkout.android_sdk.Input

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputFilter
import android.util.AttributeSet
import android.view.View.OnFocusChangeListener
import com.checkout.android_sdk.Presenter.CardInputPresenter
import com.checkout.android_sdk.Presenter.PresenterStore
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

    private var mDataStore = DataStore.getInstance()
    private var mCardInputListener: CardInput.Listener? = null

    /**
     * The UI initialisation
     *
     *
     * Used to initialise element as well as setting up appropriate listeners
     */
    init {
        val cardInputPresenter = PresenterStore.getOrCreate(
            CardInputPresenter::class.java,
            { CardInputPresenter(this, mDataStore) })

        // Add listener for text input
        addTextChangedListener(object : AfterTextChangedListener() {
            override fun afterTextChanged(text: Editable) {
                cardInputPresenter.textChanged(text)
            }
        })

        // Add listener for focus

        // When the CardInput loses focus check if the card number is not valid and trigger an error
        onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            cardInputPresenter.focusChanged(hasFocus, mDataStore.cardNumber)
        }
    }

    override fun onCardInputStateUpdated(cardInputResult: CardInputPresenter.CardInputUiState) {
        // Get Card type
        filters =
                arrayOf<InputFilter>(InputFilter.LengthFilter(cardInputResult.cardType.maxCardLength))
        // Set the CardInput icon based on the type of card
        setCardTypeIcon(cardInputResult.cardType)

        // Show or clear errors based on cardInputResult
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
     * This method is used to validate the card number
     * TODO: This is duplicated in CardInputUseCase.. should remove
     */
    fun checkIfCardIsValid(number: String, cardType: CardUtils.Cards) {
        var hasDesiredLength = false
        for (i in cardType.cardLength) {
            if (i == number.length) {
                hasDesiredLength = true
                break
            }
        }
        if (CardUtils.isValidCard(number) && hasDesiredLength) {
            if (mCardInputListener != null) {
                mCardInputListener!!.onCardInputFinish(sanitizeEntry(number))
            }
            mDataStore.cvvLength = cardType.maxCvvLength
        }
    }

    /**
     * This method will display a card icon associated to the specific card scheme
     */
    fun setCardTypeIcon(type: CardUtils.Cards) {
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

    /**
     * Used to set the callback listener for when the card input is completed
     */
    fun setCardListener(listener: Listener) {
        this.mCardInputListener = listener
    }

    /**
     * An interface needed to communicate with the parent once the field is successfully completed
     */
    interface Listener {
        fun onCardInputFinish(number: String)

        fun onCardError()

        fun onClearCardError()
    }

    companion object {

        /**
         * This method will clear the whitespace in a number string
         */
        fun sanitizeEntry(entry: String): String {
            return entry.replace("\\D".toRegex(), "")
        }
    }
}
