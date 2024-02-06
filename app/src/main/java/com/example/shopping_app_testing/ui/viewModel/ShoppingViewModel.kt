package com.example.shopping_app_testing.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shopping_app_testing.data.local.ShoppingItem
import com.example.shopping_app_testing.data.remote.responses.ImageResponse
import com.example.shopping_app_testing.repositories.ShoppingRepository
import com.example.shopping_app_testing.utils.Event
import com.example.shopping_app_testing.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val repo:ShoppingRepository
):ViewModel() {

    val shoppingItems = repo.observeAllShoppingItems()

    val totalPrice = repo.observeTotalPrice()

    private val _images = MutableLiveData<Event<Resource<ImageResponse>>>()
    val image:LiveData<Event<Resource<ImageResponse>>> = _images

    private val _currentImgUrl = MutableLiveData<String>()
    val currentImgUrl:LiveData<String> = _currentImgUrl

    private val _insertShoppingItemStatus = MutableLiveData<Event<Resource<ShoppingItem>>>()
    val insertShoppingItemStatus:LiveData<Event<Resource<ShoppingItem>>> = _insertShoppingItemStatus

    fun setCurImageUrl(url: String) {
        _currentImgUrl.postValue(url)
    }

    fun deleteShoppingItem(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repo.deleteShoppingItem(shoppingItem)
    }

    fun insertShoppingItemIntoDb(shoppingItem: ShoppingItem) = viewModelScope.launch {
        repo.insertShoppingItem(shoppingItem)
    }

    fun insertShoppingItem(name: String, amountString: String, priceString: String) {

    }

    fun searchForImage(imageQuery: String) {

    }


}