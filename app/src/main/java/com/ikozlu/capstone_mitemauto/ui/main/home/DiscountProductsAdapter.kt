package com.ikozlu.capstone_mitemauto.ui.main.home

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ikozlu.capstone_mitemauto.R
import com.ikozlu.capstone_mitemauto.common.loadImage
import com.ikozlu.capstone_mitemauto.common.visible
import com.ikozlu.capstone_mitemauto.data.model.ProductUI
import com.ikozlu.capstone_mitemauto.databinding.ProductItemBinding

class DiscountProductsAdapter(
    private val discountProductListener: DiscountProductListener
) : ListAdapter<ProductUI, DiscountProductsAdapter.DiscountProductViewHolder>(
    DiscountProductDiffCallBack()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscountProductViewHolder =
        DiscountProductViewHolder(
            ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            discountProductListener
        )

    override fun onBindViewHolder(holder: DiscountProductViewHolder, position: Int) =
        holder.bind(getItem(position))

    class DiscountProductViewHolder(
        private val binding: ProductItemBinding,
        private val productListener: DiscountProductListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductUI) = with(binding) {
            tvTitle.text = product.title
            tvSalePrice.text = product.description
            ivProduct.loadImage(product.imageOne)


            var isFavorite = product.isFavorite

            if (isFavorite) {
                ivFavorite.setImageResource(R.drawable.ic_fav_selected)
            }

            ivFavorite.setOnClickListener {
                isFavorite = !isFavorite
                ivFavorite.apply {
                    if (isFavorite) {
                        ivFavorite.setImageResource(R.drawable.ic_fav_selected)
                    } else {
                        ivFavorite.setImageResource(R.drawable.ic_fav_unselected)
                    }
                }
                productListener.onFavButtonClick(product)
            }

            ivProduct.setOnClickListener {
                productListener.onProductClick(product.id)
            }

            btnAddCart.setOnClickListener {
                productListener.onCartButtonClick(product.id)
            }

            if (product.saleState == true) {
                tvPrice.textSize = 12f
                tvSalePrice.visible()
                tvSalePrice.text = "₺${product.salePrice}"
                tvPrice.setText(Html.fromHtml("<s>₺${product.price}</s>"))
            } else {
                tvPrice.text = "₺${product.price}"
            }
        }
    }

    class DiscountProductDiffCallBack : DiffUtil.ItemCallback<ProductUI>() {
        override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem.id == newItem.id
        }
    }

    interface DiscountProductListener {
        fun onProductClick(id: Int)
        fun onCartButtonClick(id: Int)
        fun onFavButtonClick(product: ProductUI) {

        }
    }
}

