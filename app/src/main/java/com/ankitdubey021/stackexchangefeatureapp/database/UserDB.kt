package com.ankitdubey021.stackexchangefeatureapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserDB constructor(
    @PrimaryKey
    val userId : Int,
    val display_name : String,
    val profile_image : String,
    val location : String,
    val reputation  : Int,
    val silverBadge : Int,
    val bronzeBadge : Int,
    val goldBadge :Int,
    val website_url : String
)
