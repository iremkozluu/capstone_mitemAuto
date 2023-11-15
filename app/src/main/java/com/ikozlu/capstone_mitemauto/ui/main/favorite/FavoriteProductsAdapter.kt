package com.ikozlu.capstone_mitemauto.ui.main.favorite

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ikozlu.capstone_mitemauto.common.loadImage
import com.ikozlu.capstone_mitemauto.common.visible
import com.ikozlu.capstone_mitemauto.data.model.ProductUI
import com.ikozlu.capstone_mitemauto.databinding.ItemFavoriteBinding

class FavoriteProductsAdapter(
    private val productListener: ProductListener
) : ListAdapter<ProductUI, FavoriteProductsAdapter.ProductViewHolder>(ProductDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder =
        ProductViewHolder(
            ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            productListener
        )

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(getItem(position))

    class ProductViewHolder(
        private val binding: ItemFavoriteBinding,
        private val productListener: ProductListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductUI) = with(binding) {
            tvTitle.text = product.title
            tvDesc.text = product.description
            ivProduct.loadImage(product.imageOne)

            ivProduct.setOnClickListener {
                productListener.onProductClick(product.id)
            }

            ivFav.setOnClickListener {
                productListener.onFavButtonClick(product)
            }

            if (product.saleState == true) {
                tvPrice.textSize = 14f
                tvSalePrice.visible()
                tvSalePrice.text = "₺${product.salePrice}"
                tvPrice.setText(Html.fromHtml("<s>₺${product.price}</s>"))
            } else {
                tvPrice.text = "₺${product.price}"
            }
        }
    }

    class ProductDiffCallBack : DiffUtil.ItemCallback<ProductUI>() {
        override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem.id == newItem.id
        }
    }

    interface ProductListener {
        fun onProductClick(id: Int)
        fun onFavButtonClick(product: ProductUI)
    }
}

