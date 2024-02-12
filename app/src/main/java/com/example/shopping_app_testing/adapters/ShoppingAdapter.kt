package com.example.shopping_app_testing.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.shopping_app_testing.data.local.ShoppingItem
import com.example.shopping_app_testing.databinding.ItemShoppingBinding
import javax.inject.Inject

class ShoppingAdapter @Inject constructor(
    private val glide:RequestManager
):RecyclerView.Adapter<ShoppingAdapter.ShoppingItemViewHolder>() {

    inner class ShoppingItemViewHolder(binding: ItemShoppingBinding): RecyclerView.ViewHolder(binding.root){
        private val name = binding.tvName
        private val amount = binding.tvShoppingItemAmount
        private val price = binding.tvShoppingItemPrice
        private val image = binding.ivShoppingImage

        fun bind(item:ShoppingItem){
            glide.load(item.imageUrl).into(image)
            name.text = item.name
            amount.text = item.amount.toString()
            price.text = item.price.toString()

        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<ShoppingItem>(){
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

     var shoppingItems:List<ShoppingItem>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        return ShoppingItemViewHolder(
            ItemShoppingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        val item = shoppingItems[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return shoppingItems.size
    }
}