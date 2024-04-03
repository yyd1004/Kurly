package com.example.kurly

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kurly.common.Config
import com.example.kurly.common.dpToPx
import com.example.kurly.common.formatComma
import com.example.kurly.databinding.ItemProductBinding
import com.example.kurly.response.ProductsData
import kotlin.math.ceil

class ItemProductAdapter(val type: Int): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemList : ArrayList<ProductsData.Products> = ArrayList()

    override fun getItemCount() = itemList.size

    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        context = parent.context

        return  ItemViewHolder(
            ItemProductBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    inner class ItemViewHolder(private val binding: ItemProductBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(product: ProductsData.Products){

            binding.apply {
                if (type == Config.GRID_TYPE) {
                    llImage.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                    flImage.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                    ivImage.layoutParams.height = ((context.resources.displayMetrics.widthPixels / 3) / 3 * 4)
                    tvName.maxLines = 2
                } else if (type == Config.HORIZONTAL_TYPE) {
                    llImage.layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
                    flImage.layoutParams.width = (((context.resources.displayMetrics.widthPixels - dpToPx(10)) / 3))
                    ivImage.layoutParams.height = ((context.resources.displayMetrics.widthPixels / 3) / 3 * 4)
                    tvName.maxLines = 2
                } else {
                    llImage.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                    flImage.layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
                    ivImage.layoutParams.height = (((context.resources.displayMetrics.widthPixels - dpToPx(10)) / 6) * 4)
                    tvName.maxLines = 1
                }

                ivLike.setOnClickListener {
                    if ((Config.loadLikeArrayList(context, "like_list"))?.firstOrNull { it.equals(product.id.toString()) }.isNullOrEmpty()) {
                        ivLike.setImageResource(R.drawable.ic_btn_heart_on)
                        val likeArrayList = Config.loadLikeArrayList(context, "like_list")
                        likeArrayList?.let {
                            it.add("${product.id}")
                            Config.saveLikeArrayList(context, it, "like_list")
                        }
                    } else {
                        ivLike.setImageResource(R.drawable.ic_btn_heart_off)
                        val likeArrayList = Config.loadLikeArrayList(context, "like_list")
                        likeArrayList?.let {
                            it.remove("${product.id}")
                            Config.saveLikeArrayList(context, it, "like_list")
                        }
                    }
                }

                name = product.name
                url = product.image

                tvOriginalprice.setPaintFlags(tvOriginalprice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

                if (product.discountedPrice == null || product.discountedPrice == 0f) {
                    tvDiscount.visibility = View.GONE
                    tvOriginalprice.visibility = View.GONE
                    tvOriginalpriceVertical.visibility = View.GONE

                    product.originalPrice?.let {
                        discountedPrice = context.getString(R.string.price_won, it.formatComma())
                    }
                } else {
                    tvDiscount.visibility = View.VISIBLE
                    if (type == Config.VERTICAL_TYPE) {
                        tvOriginalprice.visibility = View.GONE
                        tvOriginalpriceVertical.visibility = View.VISIBLE
                    } else {
                        tvOriginalprice.visibility = View.VISIBLE
                        tvOriginalpriceVertical.visibility = View.GONE
                    }

                    discountedPrice = context.getString(R.string.price_won, product.discountedPrice?.formatComma())
                    product.originalPrice?.let {
                        originalPrice = context.getString(R.string.price_won, it.formatComma())
                        discount = "${ceil(((it - product.discountedPrice) / it) * 100).toInt()}%"
                    }
                }

                if (!(Config.loadLikeArrayList(context, "like_list"))?.firstOrNull { it.equals(product.id.toString()) }.isNullOrEmpty()) {
                    ivLike.setImageResource(R.drawable.ic_btn_heart_on)
                } else {
                    ivLike.setImageResource(R.drawable.ic_btn_heart_off)
                }
            }
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).bind(itemList[position])
    }

    fun submitList(arrayList: ArrayList<ProductsData.Products>) {
        itemList.clear()
        itemList.addAll(arrayList)
        notifyDataSetChanged()
    }
}