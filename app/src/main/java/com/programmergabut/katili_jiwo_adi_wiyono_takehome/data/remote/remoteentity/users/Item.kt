package com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.remoteentity.users

/*
   Created by Katili Jiwo A.W. 11 January 2021
*/

import com.google.gson.annotations.SerializedName

data class Item(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
)