package com.ankitdubey021.stackexchangefeatureapp.data

import com.ankitdubey021.stackexchangefeatureapp.database.UserDB
import java.io.Serializable

data class User(
    val user_id : Int,
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

const val DATABASE_DB_NAME="stack_exchange.db"

fun User.asDBUser() : UserDB {
    return UserDB(
        userId = this.user_id,
        display_name = this.display_name,
        location = this.location,
        reputation = this.reputation,
        website_url = this.website_url,
        profile_image = this.profile_image,
        silverBadge = this.badge_counts.silver,
        bronzeBadge = this.badge_counts.bronze,
        goldBadge = this.badge_counts.gold
    )
}
