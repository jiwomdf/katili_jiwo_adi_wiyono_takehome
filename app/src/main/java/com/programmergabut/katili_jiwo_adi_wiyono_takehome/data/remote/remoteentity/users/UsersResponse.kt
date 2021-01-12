package com.programmergabut.katili_jiwo_adi_wiyono_takehome.data.remote.remoteentity.users


import com.google.gson.annotations.SerializedName
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.base.BaseResponse

class UsersResponse : BaseResponse() {
    @SerializedName("message")
    var message: String = ""
    @SerializedName("documentation_url")
    var documentationUrl: String = ""
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean = false
    @SerializedName("items")
    lateinit var items: List<Item>
    @SerializedName("total_count")
    var totalCount: Int = 0
}