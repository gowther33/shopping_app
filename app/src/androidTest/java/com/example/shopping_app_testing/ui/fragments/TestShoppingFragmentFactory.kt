package com.example.shopping_app_testing.ui.fragments

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.example.shopping_app_testing.adapters.ImageAdapter
import com.example.shopping_app_testing.adapters.ShoppingAdapter
import com.example.shopping_app_testing.repositories.FakeShoppingRepositoryAndroidTest
import com.example.shopping_app_testing.ui.fragments.AddShoppingItemFragment
import com.example.shopping_app_testing.ui.fragments.ImagePickFragment
import com.example.shopping_app_testing.ui.viewModel.ShoppingViewModel
import javax.inject.Inject

class TestShoppingFragmentFactory @Inject constructor(
    private val imageAdapter:ImageAdapter,
    private val glide:RequestManager,
    private val shoppingAdapter : ShoppingAdapter
):FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ImagePickFragment::class.java.name -> ImagePickFragment(imageAdapter)
            AddShoppingItemFragment::class.java.name -> AddShoppingItemFragment(glide)
            ShoppingFragment::class.java.name -> ShoppingFragment(
                shoppingAdapter,
                ShoppingViewModel(FakeShoppingRepositoryAndroidTest())
            )
            else -> super.instantiate(classLoader, className)
        }
    }

}