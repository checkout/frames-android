package com.checkout.kotlinsample.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.checkout.android_sdk.PaymentForm
import com.checkout.android_sdk.Response.CardTokenisationFail
import com.checkout.android_sdk.Response.CardTokenisationResponse
import com.checkout.android_sdk.Utils.Environment
import com.checkout.android_sdk.network.NetworkError
import com.checkout.kotlinsample.R
import com.checkout.kotlinsample.databinding.FragmentSdkUiDemoBinding
import com.checkout.kotlinsample.presentation.ui.MainActivity
import com.checkout.kotlinsample.presentation.alert
import com.checkout.kotlinsample.presentation.neutralButton
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class CheckOutSDKUIFragment : Fragment() {

    private var _binding: FragmentSdkUiDemoBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSdkUiDemoBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        binding.checkoutCardForm
            .setFormListener(mFormListener)
            .setEnvironment(Environment.SANDBOX)
            .setKey(getString(R.string.secret_key))
            .setDefaultBillingCountry(Locale.UK)
        // hide toolbar
        (activity as MainActivity?)?.supportActionBar?.hide()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // Callback used for the Payment Form interaction
    private val mFormListener by lazy {
        object : PaymentForm.PaymentFormCallback {
            override fun onFormSubmit() {
                binding.progressBarCheckOut.visibility = View.VISIBLE
            }

            override fun onTokenGenerated(response: CardTokenisationResponse) {
                binding.checkoutCardForm.clearForm() // clear the form
                // dismiss the loader
                binding.progressBarCheckOut.visibility = View.GONE
                displayMessage(getString(R.string.token_generated), response.token)
            }

            override fun onError(response: CardTokenisationFail) {
                binding.progressBarCheckOut.visibility = View.GONE
                displayMessage(getString(R.string.token_error), response.errorType)
            }

            override fun onNetworkError(error: NetworkError) {
                binding.progressBarCheckOut.visibility = View.GONE
                displayMessage(getString(R.string.network_error), error.toString())
            }

            override fun onBackPressed() {
                findNavController().navigate(R.id.action_CheckOutSDKUIFragment_to_ProceedOrderFragment)
            }
        }
    }

    private fun displayMessage(title: String, message: String) {
        requireContext().alert {
            setTitle(title)
            setMessage(message)
            neutralButton { }
        }

    }
}