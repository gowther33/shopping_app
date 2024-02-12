package com.example.shopping_app_testing.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.shopping_app_testing.databinding.FragmentAddShoppingItemBinding
import com.example.shopping_app_testing.databinding.FragmentImagePickBinding
import com.example.shopping_app_testing.ui.viewModel.ShoppingViewModel

class ImagePickFragment:Fragment() {

    private var _binding: FragmentImagePickBinding? = null
    val binding: FragmentImagePickBinding
        get() = _binding!!

    private val viewModel: ShoppingViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImagePickBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}