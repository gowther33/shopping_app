package com.example.shopping_app_testing.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.shopping_app_testing.databinding.FragmentShoppingBinding
import com.example.shopping_app_testing.ui.viewModel.ShoppingViewModel

class ShoppingFragment : Fragment() {

    private lateinit var binding: FragmentShoppingBinding

    private val viewModel: ShoppingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShoppingBinding.inflate(
            inflater,
            container, false,
        )
        return binding.root
    }

}