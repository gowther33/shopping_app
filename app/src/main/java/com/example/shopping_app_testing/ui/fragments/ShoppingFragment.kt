package com.example.shopping_app_testing.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.LEFT
import androidx.recyclerview.widget.ItemTouchHelper.RIGHT
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopping_app_testing.adapters.ShoppingAdapter
import com.example.shopping_app_testing.databinding.FragmentShoppingBinding
import com.example.shopping_app_testing.ui.viewModel.ShoppingViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ShoppingFragment  @Inject constructor(
    val shoppingItemAdapter: ShoppingAdapter,
    var viewModel : ShoppingViewModel? = null
): Fragment() {
    private var _binding: FragmentShoppingBinding? = null
    private val binding: FragmentShoppingBinding
        get() = _binding!!

//    private val viewModel: ShoppingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = viewModel ?: ViewModelProvider(requireActivity())[ShoppingViewModel::class.java]
        subscribeToObservers()
        setUpRecyclerView()

        binding.fabAddShoppingItem.setOnClickListener {
            val action = ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment()
            findNavController().navigate(action)
        }
    }

    private val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
        0,
        LEFT or RIGHT,
    ){
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = true

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.layoutPosition
            val item = shoppingItemAdapter.shoppingItems[pos]
            viewModel?.deleteShoppingItem(item)
            Snackbar.make(
                requireView(),
                "Successfully deleted item",
                Snackbar.LENGTH_LONG
            ).apply {
                setAction("Undo"){
                    viewModel?.insertShoppingItemIntoDb(item)
                }
                show()
            }

        }
    }

    private fun setUpRecyclerView(){
        binding.rvShoppingItems.apply {
            adapter = shoppingItemAdapter
            layoutManager = LinearLayoutManager(requireContext())
            ItemTouchHelper(itemTouchCallback).attachToRecyclerView(this)
        }
    }

    private fun subscribeToObservers(){
        viewModel?.shoppingItems?.observe(viewLifecycleOwner){
            shoppingItemAdapter.shoppingItems = it
        }

        viewModel?.totalPrice?.observe(viewLifecycleOwner){
            val price = it ?: 0f
            val priceText = "Total Price: Rs.$price"
            binding.tvShoppingItemPrice.text = priceText
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}