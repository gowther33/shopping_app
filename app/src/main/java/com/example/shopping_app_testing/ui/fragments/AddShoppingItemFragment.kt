package com.example.shopping_app_testing.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.shopping_app_testing.databinding.FragmentAddShoppingItemBinding
import com.example.shopping_app_testing.ui.viewModel.ShoppingViewModel

class AddShoppingItemFragment : Fragment() {

    private var _binding: FragmentAddShoppingItemBinding? = null
    private val binding:FragmentAddShoppingItemBinding
        get() = _binding!!

    private val viewModel: ShoppingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddShoppingItemBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ivShoppingImage.setOnClickListener {
            val action = AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
            findNavController().navigate(action)
        }


        val backCallback = object: OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                viewModel.setCurImageUrl("")
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(backCallback)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}