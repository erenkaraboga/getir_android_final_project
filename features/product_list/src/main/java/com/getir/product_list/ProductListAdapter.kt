package com.getir.product_list

import android.annotation.SuppressLint
import android.graphics.Movie
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.getir.core.databinding.CustomProductItemBinding
import com.getir.core.domain.models.Product


class ProductListAdapter(


) :

    RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private val data = ArrayList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = CustomProductItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(
            binding
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(items: List<Product>) {
        this.data.clear()
        this.data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) { bind(item = data[position]) }
    }

    override fun getItemCount(): Int {
        return data.size
    }


    inner class ViewHolder(val binding: CustomProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(item: Product) {
           binding.tvPrice.text = item.priceText
            binding.tvProductName.text = item.name
            binding.tvAttribute.text = item.attribute
            Glide.with(itemView.context).load(item.imageURL).into(binding.ivProductImage)

        }

    }
}

interface MyStockAdapterListener {
    fun onClickedItem(investmentId: String, symbol: String)
}