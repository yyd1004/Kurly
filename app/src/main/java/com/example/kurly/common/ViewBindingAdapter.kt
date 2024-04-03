package com.example.kurly.common

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.kurly.R

object ViewBindingAdapter {
    @BindingAdapter("loadUrlImage")
    @JvmStatic
    fun loadUrlImage(view: ImageView, url: String?) {
        Glide.with(view.context)
            .load(url)
            .error(R.drawable.ic_launcher_foreground)
            .into(view)
    }
}