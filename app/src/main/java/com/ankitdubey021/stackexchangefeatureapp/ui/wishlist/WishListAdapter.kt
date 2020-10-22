package com.ankitdubey021.stackexchangefeatureapp.ui.wishlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ankitdubey021.stackexchangefeatureapp.R
import com.ankitdubey021.stackexchangefeatureapp.database.UserDB
import com.ankitdubey021.stackexchangefeatureapp.databinding.ListFavUsersBinding

class WishAdapter (val callback: (UserDB)-> Unit):
    RecyclerView.Adapter<WishAdapter.MyViewHolder>() {

    var daos: List<UserDB> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val withDataBinding: ListFavUsersBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            MyViewHolder.LAYOUT,
            parent,
            false)
        return MyViewHolder(withDataBinding)

    }

    override fun getItemCount() = daos.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.dao = daos[position]
            it.sparkButton.setOnClickListener { callback(daos[position])}
        }
    }

    class MyViewHolder(val viewDataBinding: ListFavUsersBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.list_fav_users
        }
    }
}