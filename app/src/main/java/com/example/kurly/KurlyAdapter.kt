package com.example.kurly

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kurly.common.Config
import com.example.kurly.common.dpToPx
import com.example.kurly.databinding.ItemBigBinding
import com.example.kurly.databinding.ItemSmallBinding
import com.example.kurly.response.SectionsData

class KurlyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var context: Context

    var itemList: ArrayList<SectionsData.Sections> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        context = parent.context

        if (viewType == Config.GRID_TYPE) {
            return GridHolder(ItemSmallBinding.inflate(LayoutInflater.from(parent.context), parent,false))
        } else if (viewType == Config.HORIZONTAL_TYPE) {
            return HorizontalHolder(ItemSmallBinding.inflate(LayoutInflater.from(parent.context), parent,false))
        } else {
            return VerticalHolder(ItemBigBinding.inflate(LayoutInflater.from(parent.context), parent,false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            Config.HORIZONTAL_TYPE -> {
                (holder as HorizontalHolder).bind(itemList[position])
            }
            Config.GRID_TYPE -> {
                (holder as GridHolder).bind(itemList[position])
            }
            Config.VERTICAL_TYPE -> {
                (holder as VerticalHolder).bind(itemList[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (itemList.get(position).type == "grid") {
            return Config.GRID_TYPE
        } else if (itemList.get(position).type == "horizontal") {
            return Config.HORIZONTAL_TYPE
        } else {
            return Config.VERTICAL_TYPE
        }
    }

    override fun getItemCount(): Int {
        return  itemList.size
    }

    inner class HorizontalHolder(private val binding: ItemSmallBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SectionsData.Sections) {
            binding.apply {
                title = item.title

                val adapter = ItemProductAdapter(Config.HORIZONTAL_TYPE)
                rvList.adapter = adapter
                rvList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                rvList.setPadding(dpToPx(5), 0, dpToPx(5), 0)
                item.products?.let {
                    adapter.submitList(it)
                }
            }
        }
    }

    inner class GridHolder(private val binding: ItemSmallBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SectionsData.Sections) {
            binding.apply {
                title = item.title

                val adapter = ItemProductAdapter(Config.GRID_TYPE)
                rvList.adapter = adapter
                rvList.layoutManager = GridLayoutManager(context, 3)
                rvList.setPadding(dpToPx(5), 0, dpToPx(5), 0)
                item.products?.let {
                    adapter.submitList(it)
                }
            }
        }
    }

    inner class VerticalHolder(private val binding: ItemBigBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SectionsData.Sections) {
            binding.apply {
                title = item.title

                val adapter = ItemProductAdapter(Config.VERTICAL_TYPE)
                rvList.adapter = adapter
                rvList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                rvList.setPadding(dpToPx(5), 0, dpToPx(5), 0)
                item.products?.let {
                    adapter.submitList(it)
                }
            }
        }
    }

    fun submitList(arrayList: ArrayList<SectionsData.Sections>) {
        itemList.addAll(arrayList)
        notifyDataSetChanged()
    }
}