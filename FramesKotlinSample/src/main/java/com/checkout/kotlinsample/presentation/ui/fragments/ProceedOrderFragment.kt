package com.checkout.kotlinsample.presentation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.checkout.kotlinsample.R
import com.checkout.kotlinsample.databinding.FragmentOrderProceedBinding
import com.checkout.kotlinsample.presentation.ui.MainActivity
import com.checkout.kotlinsample.presentation.isInternetConnected
import com.checkout.kotlinsample.presentation.snackbar

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class ProceedOrderFragment : Fragment() {

    private var _binding: FragmentOrderProceedBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentOrderProceedBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBuy.setOnClickListener {
            if (!requireContext().isInternetConnected) binding.imgItemFood.snackbar(getString(R.string.connection_error))
            else findNavController().navigate(R.id.action_ProceedOrderFragment_to_CheckoutSDKUIFragment)
        }
        //show toolbar
        (activity as MainActivity?)?.supportActionBar?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}