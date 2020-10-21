package com.ankitdubey021.stackexchangefeatureapp.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import timber.log.Timber
import java.util.regex.Matcher
import java.util.regex.Pattern

inline fun <reified T:Any> newIntent(context: Context): Intent = Intent(context,T::class.java)

inline fun <reified T : Any> Activity.launchActivity(
    requestCode:Int =-1,
    options: Bundle?=null,
    noinline init: Intent.() -> Unit = {}){

    val intent = newIntent<T>(this)
    intent.init()
    startActivityForResult(intent,requestCode,options)
}

fun Context.toast(msg : String){
    Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
}

fun View.hide(){
    this.visibility = View.GONE
}
fun View.show(){
    this.visibility = View.VISIBLE
}

fun Context.getColorRes(@ColorRes id: Int) = ContextCompat.getColor(applicationContext, id)


fun RecyclerView.xOnScrollListener(load : () -> Unit){

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val totalItemCount = recyclerView!!.layoutManager!!.itemCount
            val lastVisibleItemPosition = (recyclerView!!.layoutManager!! as GridLayoutManager).findLastVisibleItemPosition()

            if (totalItemCount == lastVisibleItemPosition + 1)
                load()
        }
    }
    this.addOnScrollListener(scrollListener)
}

fun ImageView.loadImgUrl(path : String){
    Glide.with(this.context)
        .load(path)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}
