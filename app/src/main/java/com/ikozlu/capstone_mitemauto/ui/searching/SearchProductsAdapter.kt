package com.ikozlu.capstone_mitemauto.ui.searching

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
import com.ikozlu.capstone_mitemauto.databinding.ItemSearchBinding

class SearchProductsAdapter(
    private val searchProductListener: SearchProductListener
) : ListAdapter<ProductUI, SearchProductsAdapter.SearchProductViewHolder>(SearchProductDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchProductViewHolder =
        SearchProductViewHolder(
            ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            searchProductListener
        )

    override fun onBindViewHolder(holder: SearchProductViewHolder, position: Int) =
        holder.bind(getItem(position))

    class SearchProductViewHolder(
        private val binding: ItemSearchBinding,
        private val searchProductListener: SearchProductListener
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductUI) = with(binding) {
            tvTitle.text = product.title
            ivProduct.loadImage(product.imageOne)

            var isFavorite = product.isFavorite

            ivProduct.setOnClickListener {
                searchProductListener.onProductClick(product.id)
            }

            btnAddCart.setOnClickListener {
                searchProductListener.onCartButtonClick(product.id)
            }

            if (isFavorite) {
                ivFavorite.setImageResource(R.drawable.ic_fav_selected)
            }

            ivFavorite.setOnClickListener {
                isFavorite = !isFavorite
                ivFavorite.apply {
                    if (isFavorite) {
                        searchProductListener.onFavButtonClick(product)
                        ivFavorite.setImageResource(R.drawable.ic_fav_selected)
                    } else {
                        ivFavorite.setImageResource(R.drawable.ic_fav_unselected)
                    }
                }
            }


            if (product.saleState == true) {
                tvPrice.textSize = 14f
                tvPrice.setText(Html.fromHtml("<s>₺${product.price}</s>"))
                tvSalePrice.visible()
                tvSalePrice.text = "₺${product.salePrice}"
            } else {
                tvPrice.text = "₺${product.price}"
            }
        }
    }

    class SearchProductDiffCallBack : DiffUtil.ItemCallback<ProductUI>() {
        override fun areContentsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: ProductUI, newItem: ProductUI): Boolean {
            return oldItem.id == newItem.id
        }
    }

    interface SearchProductListener {
        fun onProductClick(id: Int)
        fun onCartButtonClick(id: Int)
        fun onFavButtonClick(product: ProductUI)
    }
}

