package com.programmergabut.katili_jiwo_adi_wiyono_takehome.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {
    companion object {
        const val SHOW_LOADING : Int = 1
        const val REMOVE_LOADING : Int = 2

        const val SUCCESS = 3
        const val ERROR = 4
    }
    val loading : MutableLiveData<Int> = MutableLiveData()
}