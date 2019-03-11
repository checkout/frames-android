package com.checkout.sdk.cvvinput

import android.content.Context
import android.text.Editable
import android.util.AttributeSet
import android.view.View.OnFocusChangeListener
import com.checkout.sdk.architecture.MvpView
import com.checkout.sdk.architecture.PresenterStore
import com.checkout.sdk.input.DefaultInput
import com.checkout.sdk.store.DataStore
import com.checkout.sdk.store.InMemoryStore
import com.checkout.sdk.utils.AfterTextChangedListener

/**
 * A custom EditText with validation and handling of cvv input
 */
class CvvInput @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    android.support.v7.widget.AppCompatEditText(context, attrs),
    MvpView<CvvInputUiState> {

    private var listener: DefaultInput.Listener? = null

    private lateinit var presenter: CvvInputPresenter

    override fun onStateUpdated(uiState: CvvInputUiState) {
        if (text.toString() != uiState.cvv) {
            setText(uiState.cvv)
        }
        listener?.let {
            it.onInputFinish(uiState.cvv)
            // TODO: Missing validation to show cvv error when we step off cvv
            if (!uiState.showError) {
                it.clearInputError()
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter = PresenterStore.getOrCreate(
            CvvInputPresenter::class.java,
            { CvvInputPresenter() })
        presenter.start(this)

        addTextChangedListener(object: AfterTextChangedListener() {
            override fun afterTextChanged(s: Editable?) {
                val cvvInputUseCase = CvvInputUseCase(InMemoryStore.Factory.get(), s.toString())
                presenter.inputStateChanged(cvvInputUseCase)
            }
        })

        onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            val cvvFocusChangedUseCase =
                CvvFocusChangedUseCase(text.toString(), hasFocus, DataStore.getInstance())
            presenter.focusChanged(cvvFocusChangedUseCase)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.stop()
    }

    /**
     * Used to set the callback listener for when the zip input is completed
     */
    fun setListener(listener: DefaultInput.Listener) {
        this.listener = listener
    }
}
