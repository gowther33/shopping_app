package com.example.shopping_app_testing.ui.fragments

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.example.shopping_app_testing.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import com.example.shopping_app_testing.R
import com.example.shopping_app_testing.adapters.ShoppingAdapter
import com.example.shopping_app_testing.data.local.ShoppingItem
import com.example.shopping_app_testing.getOrAwaitValue
import com.example.shopping_app_testing.ui.viewModel.ShoppingViewModel
import com.google.common.truth.Truth.assertThat
import org.mockito.Mockito.verify
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ShoppingFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var testFragmentFactory: TestShoppingFragmentFactory

    @Before
    fun setUp(){
        hiltRule.inject()
    }

    @Test
    fun clickAddShoppingItemButton_navigateToAddShoppingItemFragment(){
        // Creates a mock class ( all functions of the given class with only signature)
        val navController = mock(NavController::class.java)

        launchFragmentInHiltContainer<ShoppingFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.fabAddShoppingItem)).perform(click())

        verify(navController).navigate(
            ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment()
        )
    }

    @Test
    fun swipeShoppingItem_deletesItemInDb(){
        val shoppingItem = ShoppingItem("test",1,1F,"test", 1)
        var testViewModel : ShoppingViewModel? = null

        launchFragmentInHiltContainer<ShoppingFragment>(
            fragmentFactory = testFragmentFactory
        ) {
            testViewModel = viewModel
            viewModel?.insertShoppingItemIntoDb(shoppingItem)
        }

        onView(withId(R.id.rvShoppingItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ShoppingAdapter.ShoppingItemViewHolder>(
                0,
                swipeLeft()
            )
        )

        assertThat(testViewModel?.shoppingItems?.getOrAwaitValue()).isEmpty()
    }

}