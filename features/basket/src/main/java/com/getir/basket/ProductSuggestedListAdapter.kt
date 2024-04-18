package com.getir.basket

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.getir.core.R
import com.getir.core.common.ui.CustomProductItem
import com.getir.core.common.ui.CustomProductItemListener
import com.getir.core.domain.models.Product

class ProductSuggestedListAdapter(
    private val context: Context,
    private val listener: ProductSuggestedItemListener
) : RecyclerView.Adapter<ProductSuggestedListAdapter.ViewHolder>() {

    private val data = ArrayList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = CustomProductItem(context)
        return ViewHolder(itemView)
    }

    fun setItems(items: List<Product>) {
        data.clear()
        data.addAll(items)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(private val productItemView: CustomProductItem) :
        RecyclerView.ViewHolder(productItemView) {

        fun bind(item: Product) {
            productItemView.setProduct(product = item)
            productItemView.setListener(object :CustomProductItemListener{
                override fun onQuantityIncreased(quantity: Int, product: Product) {
                    listener.onProductIncreased(quantity , product)

                }

                override fun onQuantityDecreased(quantity: Int, product: Product) {
                    listener.onProductDecreased(quantity , product)

                }

                override fun onProductClicked(product: Product) {
                    listener.onProductClicked(product)
                }

            })


        }
    }
}


interface ProductSuggestedItemListener {
    fun onProductClicked(product: Product)
    fun onProductDecreased(quantity: Int, product: Product)
    fun onProductIncreased(quantity: Int, product: Product)
}