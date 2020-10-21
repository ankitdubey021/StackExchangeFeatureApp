package com.ankitdubey021.stackexchangefeatureapp.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ankitdubey021.stackexchangefeatureapp.R
import com.ankitdubey021.stackexchangefeatureapp.data.User
import com.ankitdubey021.stackexchangefeatureapp.data.UserClickCallback
import com.ankitdubey021.stackexchangefeatureapp.databinding.ListUsersBinding
import kotlinx.android.synthetic.main.list_users.view.*


class SearchAdapter (private val userClickListener: UserClickListener):
    RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    var daos: List<User> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val withDataBinding: ListUsersBinding = DataBindingUtil.inflate(
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
            it.cv.setOnClickListener { userClickListener.onUserClicked(daos[position],it.img) }
        }
    }

    class MyViewHolder(val viewDataBinding: ListUsersBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.list_users
        }
    }

    interface UserClickListener {
        fun onUserClicked(user: User, imageView: ImageView)
    }
}
