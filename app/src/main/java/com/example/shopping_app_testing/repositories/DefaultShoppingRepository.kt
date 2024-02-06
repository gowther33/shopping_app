package com.example.shopping_app_testing.repositories

import androidx.lifecycle.LiveData
import com.example.shopping_app_testing.data.local.ShoppingDao
import com.example.shopping_app_testing.data.local.ShoppingItem
import com.example.shopping_app_testing.data.remote.PixabayAPI
import com.example.shopping_app_testing.data.remote.responses.ImageResponse
import com.example.shopping_app_testing.utils.Resource
import javax.inject.Inject


class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao:ShoppingDao,
    private val pixabayAPI: PixabayAPI
) :ShoppingRepository {

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.searchForImage(imageQuery)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Unknown Error", null)
            }else{
                Resource.error("Unknown Error", null)
            }

        }catch (e:Exception){
            Resource.error("Could not reach server", null)
        }
    }
}