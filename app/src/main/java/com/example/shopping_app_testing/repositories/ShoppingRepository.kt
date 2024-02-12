package com.example.shopping_app_testing.repositories

import androidx.lifecycle.LiveData
import com.example.shopping_app_testing.data.local.ShoppingItem
import com.example.shopping_app_testing.data.remote.responses.ImageResponse
import com.example.shopping_app_testing.utils.Resource
import retrofit2.Response

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>
}