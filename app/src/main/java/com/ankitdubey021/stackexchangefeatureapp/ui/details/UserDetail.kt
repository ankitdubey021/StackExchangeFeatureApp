package com.ankitdubey021.stackexchangefeatureapp.ui.details

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.ankitdubey021.stackexchangefeatureapp.R
import com.ankitdubey021.stackexchangefeatureapp.data.User
import com.ankitdubey021.stackexchangefeatureapp.data.asDBUser
import com.ankitdubey021.stackexchangefeatureapp.database.UserDao
import com.ankitdubey021.stackexchangefeatureapp.databinding.ActivityUserDetailBinding
import com.ankitdubey021.stackexchangefeatureapp.extensions.loadImgUrl
import com.ankitdubey021.stackexchangefeatureapp.extensions.setFavActionListener
import com.varunest.sparkbutton.SparkEventListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.content_user_detail_activity.view.*
import kotlinx.android.synthetic.main.user_detail_collapse_bar.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class UserDetail : AppCompatActivity() {

    lateinit var binding : ActivityUserDetailBinding
    lateinit var user : User
    private val userViewModel : UserDetailViewModel by viewModels()
    @Inject lateinit var userDao : UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_detail)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_user_detail)

        user = intent.getSerializableExtra("dao") as User

        initViews()
        isUserAlreadyInWishList()
    }

    private fun initViews() {
        binding.collapseToolbarLayout.apply {

            label.text = user.display_name
            val imagePath = user.profile_image.replace("s=128", "s=256")
            background.loadImgUrl(imagePath)

            content_layout.apply {
                silver_chip.text = "Silver | ${user.badge_counts.silver}"
                bronze_chip.text = "Bronze | ${user.badge_counts.bronze}"
                gold_chip.text = "Gold | ${user.badge_counts.gold}"

                tv_reputation.text = "${user.reputation}"


                spark_button.setFavActionListener { isChecked ->
                    user.asDBUser().let {
                        if (isChecked)
                            userViewModel.addToFavorite(it)
                        else
                            userViewModel.removeFromFavorite(it)
                    }
                }
            }
        }
    }

    private fun isUserAlreadyInWishList() {
        lifecycleScope.launch(Dispatchers.IO){
            val list = userDao.find(user.user_id)

            if(list.isNotEmpty())
                binding.contentLayout.sparkButton.isChecked=true
        }
    }
}