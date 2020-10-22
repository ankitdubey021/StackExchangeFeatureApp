package com.ankitdubey021.stackexchangefeatureapp.extensions

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions


@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()) {

        Glide.with(view.context)
            .load(imageUrl.replace("s=128","s=256"))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}