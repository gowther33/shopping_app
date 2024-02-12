package com.example.shopping_app_testing.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.example.shopping_app_testing.databinding.FragmentAddShoppingItemBinding
import com.example.shopping_app_testing.ui.viewModel.ShoppingViewModel
import com.example.shopping_app_testing.utils.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddShoppingItemFragment @Inject constructor(
    val glide:RequestManager
) : Fragment() {

    private var _binding: FragmentAddShoppingItemBinding? = null
    private val binding:FragmentAddShoppingItemBinding
        get() = _binding!!

//    private val viewModel: ShoppingViewModel by activityViewModels()

    lateinit var viewModel:ShoppingViewModel

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
        viewModel = ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]
        subscribeToObservers()

        binding.btnAddShoppingItem.setOnClickListener {
            viewModel.insertShoppingItem(
                binding.etShoppingItemName.text.toString(),
                binding.etShoppingItemAmount.text.toString(),
                binding.etShoppingItemPrice.text.toString()
            )
        }

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

    private fun subscribeToObservers(){
        viewModel.currentImgUrl.observe(viewLifecycleOwner){
            glide.load(it).into(binding.ivShoppingImage)
        }

        viewModel.insertShoppingItemStatus.observe(viewLifecycleOwner){
            it.getContentIfNotHandled()?.let {result->
                when(result.status){
                    Status.SUCCESS -> {
                        Snackbar.make(requireView().rootView,
                            "Shopping Item Added",
                            Snackbar.LENGTH_LONG
                        ).show()
                        findNavController().popBackStack()
                    }

                    Status.ERROR ->{
                        Snackbar.make(requireView().rootView,
                            "Error occurred",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    Status.LOADING -> {
                        /* NO-OP */
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}