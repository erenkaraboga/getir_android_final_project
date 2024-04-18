package com.getir.basket

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.getir.core.common.ui.CustomProductItem
import com.getir.core.common.ui.CustomProductItemBasket
import com.getir.core.common.ui.CustomProductItemBasketListener
import com.getir.core.common.ui.CustomProductItemListener
import com.getir.core.domain.models.Product

class BasketListAdapter(
    private val context: Context,
    private val listener: ProductItemListener
) : RecyclerView.Adapter<BasketListAdapter.ViewHolder>() {

    private val data = ArrayList<Product>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = CustomProductItemBasket(context)
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

    inner class ViewHolder(private val productItemView: CustomProductItemBasket) :
        RecyclerView.ViewHolder(productItemView) {

        fun bind(item: Product) {

            productItemView.setProduct(product = item)
            productItemView.setListener(object :CustomProductItemBasketListener{
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


interface ProductItemListener {
    fun onProductClicked(product: Product)
    fun onProductDecreased(quantity: Int, product: Product)
    fun onProductIncreased(quantity: Int, product: Product)
}