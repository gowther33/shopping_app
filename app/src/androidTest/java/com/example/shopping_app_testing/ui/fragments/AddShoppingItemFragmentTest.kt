package com.example.shopping_app_testing.ui.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.shopping_app_testing.R
import com.example.shopping_app_testing.data.local.ShoppingItem
import com.example.shopping_app_testing.getOrAwaitValue
import com.example.shopping_app_testing.launchFragmentInHiltContainer
import com.example.shopping_app_testing.repositories.FakeShoppingRepositoryAndroidTest
import com.example.shopping_app_testing.ui.ShoppingFragmentFactory
import com.example.shopping_app_testing.ui.viewModel.ShoppingViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class AddShoppingItemFragmentTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory : ShoppingFragmentFactory

    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @Test
    fun pressBackButton_popBackStack() {
        // Creates a mock class ( all functions of the given class with only signature)
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()

        verify(navController).popBackStack()

    }

    @Test
    fun clickInsertIntoDb_shoppingItemInsertIntoDb(){
        val testViewModel = ShoppingViewModel(FakeShoppingRepositoryAndroidTest())

        launchFragmentInHiltContainer<AddShoppingItemFragment>(
            fragmentFactory = fragmentFactory
        ) {
            viewModel = testViewModel
        }

        onView(withId(R.id.etShoppingItemName)).perform(replaceText("itemName"))
        onView(withId(R.id.etShoppingItemAmount)).perform(replaceText("5"))
        onView(withId(R.id.etShoppingItemPrice)).perform(replaceText("6.5"))
        onView(withId(R.id.btnAddShoppingItem)).perform(click())

        assertThat(
            testViewModel.shoppingItems.getOrAwaitValue()
        ).contains(ShoppingItem("itemName",5,6.5F,""))

    }

    @Test
    fun pressImageView_navigatesToImagePickFragment(){

        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.ivShoppingImage)).perform(click())

        verify(navController).navigate(
            AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
        )
    }
}