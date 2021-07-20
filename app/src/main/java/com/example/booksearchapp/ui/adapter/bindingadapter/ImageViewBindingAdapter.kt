package com.example.booksearchapp.ui.adapter.bindingadapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.booksearchapp.R

object ImageViewBindingAdapter {
    @BindingAdapter("imageUrlLarge")
    @JvmStatic
    fun loadLargeImage(ivThumbnail: ImageView, url: String) {
        val circularProgressDrawable = CircularProgressDrawable(ivThumbnail.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(ivThumbnail.context)
            .load(url)
            .placeholder(circularProgressDrawable)
            .error(R.drawable.baseline_warning_24)
            .transition(DrawableTransitionOptions().crossFade())
            .fitCenter()
            .timeout(2000)
            .into(ivThumbnail)
    }

    @BindingAdapter("imageUrlSmall")
    @JvmStatic
    fun loadSmallImage(ivThumbnail: ImageView, url: String) {
        val circularProgressDrawable = CircularProgressDrawable(ivThumbnail.context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(ivThumbnail.context)
                .load(url)
                .placeholder(circularProgressDrawable)
                .error(R.drawable.baseline_warning_24)
                .transition(DrawableTransitionOptions().crossFade())
                .centerCrop()
                .timeout(2000)
                .into(ivThumbnail)
    }
}