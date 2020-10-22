package com.ankitdubey021.stackexchangefeatureapp.ui.wishlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ankitdubey021.stackexchangefeatureapp.R
import com.ankitdubey021.stackexchangefeatureapp.databinding.FragmentWishListBinding
import com.ankitdubey021.stackexchangefeatureapp.extensions.hide
import com.ankitdubey021.stackexchangefeatureapp.extensions.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WishListFrag : Fragment() {

    lateinit var binding : FragmentWishListBinding
    private val wishViewModel  by viewModels<WishViewModel>()
    private lateinit var adapter: WishAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWishListBinding.inflate(inflater,container,false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        adapter = WishAdapter{
            wishViewModel.removeFromFavorite(it)
        }
        binding.favRv.adapter = adapter
        fetchFavs()
    }

    private fun fetchFavs() {
        wishViewModel.favUsers.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.daos= it.sortedBy { it.display_name }
            }

            if(it.isNullOrEmpty())
                binding.noDataLayout.show()
            else
                binding.noDataLayout.hide()
        })
    }
}