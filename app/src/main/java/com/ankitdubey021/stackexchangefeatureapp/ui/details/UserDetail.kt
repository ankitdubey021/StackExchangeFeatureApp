package com.ankitdubey021.stackexchangefeatureapp.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ankitdubey021.stackexchangefeatureapp.R
import com.ankitdubey021.stackexchangefeatureapp.data.User
import com.ankitdubey021.stackexchangefeatureapp.databinding.ActivityUserDetailBinding
import com.ankitdubey021.stackexchangefeatureapp.extensions.loadImgUrl
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.content_user_detail_activity.view.*
import kotlinx.android.synthetic.main.user_detail_collapse_bar.view.*

class UserDetail : AppCompatActivity() {

    lateinit var binding : ActivityUserDetailBinding
    lateinit var userDao : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_detail)

        userDao = intent.getSerializableExtra("dao") as User

        binding.collapseToolbarLayout.apply {
            label.text=userDao.display_name
            val imagePath = userDao.profile_image.replace("s=128","s=512")
            background.loadImgUrl(imagePath)

            content_layout.apply {
                silver_chip.text = "Silver | ${userDao.badge_counts.silver}"
                bronze_chip.text = "Bronze | ${userDao.badge_counts.bronze}"
                gold_chip.text = "Gold | ${userDao.badge_counts.gold}"

                tv_reputation.text="${userDao.reputation}"
            }

        }




    }
}