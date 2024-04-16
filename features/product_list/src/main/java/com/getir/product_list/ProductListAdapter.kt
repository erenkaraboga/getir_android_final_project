package com.getir.product_list

import android.annotation.SuppressLint
import android.graphics.Movie
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.getir.core.common.ui.CustomQuantityButtonDetail
import com.getir.core.common.ui.CustomQuantityButtonList
import com.getir.core.databinding.CustomProductItemBinding
import com.getir.core.domain.models.Product


class ProductListAdapter(
    private val listener: ProductItemListener


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
            binding,listener
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


    inner class ViewHolder(val binding: CustomProductItemBinding, private val listener: ProductItemListener) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        private lateinit var product: Product
        init {
            binding.root.setOnClickListener(this)
        }
        fun bind(item: Product) {
            this.product = item
           binding.tvPrice.text = item.priceText
            binding.tvProductName.text = item.name
            binding.tvAttribute.text = item.attribute
            Glide.with(itemView.context).load(item.imageURL).into(binding.ivProductImage)
            binding.customQuantityButton.setQuantity(item.quantity)
            binding.customQuantityButton.setOnQuantityChangeListener(object : CustomQuantityButtonList.OnQuantityChangeListener {
                override fun onQuantityIncreased(quantity: Int) {
                    listener.onProductIncreased(quantity,product)

                }

                override fun onQuantityDecreased(quantity: Int) {
                    listener.onProductDecreased(quantity,product)
                }

            })
        }

        override fun onClick(v: View?) {
           listener.onProductClicked(product)
        }

    }
}

interface ProductItemListener {
    fun onProductClicked(product: Product)
    fun onProductDecreased(quantity : Int , product: Product )
    fun onProductIncreased(quantity : Int,product: Product )
}
