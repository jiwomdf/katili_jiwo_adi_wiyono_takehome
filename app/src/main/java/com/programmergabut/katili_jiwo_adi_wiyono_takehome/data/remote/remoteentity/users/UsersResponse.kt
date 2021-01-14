package com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.remoteentity.users

/*
   Created by Katili Jiwo A.W. 11 January 2021
*/

import com.google.gson.annotations.SerializedName
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.base.BaseResponse

class UsersResponse : BaseResponse() {
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean = false
    @SerializedName("items")
    lateinit var items: List<Item>
    @SerializedName("total_count")
    var totalCount: Int = 0
}