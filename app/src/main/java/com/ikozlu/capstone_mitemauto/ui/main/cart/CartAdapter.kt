package com.ikozlu.capstone_mitemauto.ui.main.cart

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ikozlu.capstone_mitemauto.common.loadImage
import com.ikozlu.capstone_mitemauto.common.visible
import com.ikozlu.capstone_mitemauto.data.model.ProductUI
import com.ikozlu.capstone_mitemauto.databinding.ItemCartBinding

class CartAdapter(
    private val cartListener: CartListener
) : ListAdapter<ProductUI, CartAdapter.CartViewHolder>(CartDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder =
        CartViewHolder(
            ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            cartListener,
        )

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) =
        holder.bind(getItem(position))

    class CartViewHolder(
        private val binding: ItemCartBinding,
        private val cartListener: CartListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductUI) = with(binding) {
            tvTitle.text = product.title
            tvDesc.text = product.description
            ivProduct.loadImage(product.imageOne)

            var productCount = 1

            ivProduct.setOnClickListener {
                cartListener.onCartClick(product.id)
            }

            ivDelete.setOnClickListener {
                cartListener.onDeleteItemClick(product.id)
            }

            /*fabAdd.setOnClickListener {
                if (product.saleState) {
                    cartListener.onIncreaseItemClick(product.salePrice)
                } else {
                    cartListener.onIncreaseItemClick(product.price)
                }

                productCount++
                tvCount.text = productCount.toString()
            }

            fabRemove.setOnClickListener {
                if (productCount != 1) {
                    if (product.saleState == true) {
                        cartListener.onDecreaseItemClick(product.salePrice)
                    } else {
                        cartListener.onDecreaseItemClick(product.price)
                    }
                } else {
                    cartListener.onDeleteItemClick(product.id)
                }

                productCount--
                tvCount.text = productCount.toString()
            }*/

            if (product.saleState == true) {
                tvSalePrice.visible()
                tvSalePrice.textSize = 14f
                tvPrice.text = "₺${product.salePrice}"
                tvSalePrice.setText(Html.fromHtml("<s>₺${product.price}</s>"))

            } else {
                tvPrice.text = "₺${product.price}"

            }
        }
    }

    class CartDiffCallBack : DiffUtil.ItemCallback<ProductUI>() {
        override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem.id == newItem.id
        }
    }

    interface CartListener {
        fun onCartClick(id: Int)
        fun onDeleteItemClick(id: Int)
        fun onIncreaseItemClick(price: Double)
        fun onDecreaseItemClick(price: Double)
    }
}

