package com.ankitdubey021.stackexchangefeatureapp.data

import android.widget.ImageView
import java.io.Serializable

data class User(
    val display_name : String,
    val profile_image : String,
    val location : String,
    val reputation  : Int,
    val badge_counts : Badges,
    val website_url : String
):Serializable{
    data class Badges(
        val bronze : Int,
        val silver : Int,
        val gold : Int
    ):Serializable
}

data class UserList(
    val items : List<User>
)

class UserClickCallback(val onClicked: (User,ImageView) -> Unit) {
    fun setOnClicked(user : User, imageView: ImageView) = onClicked(user,imageView)
}

