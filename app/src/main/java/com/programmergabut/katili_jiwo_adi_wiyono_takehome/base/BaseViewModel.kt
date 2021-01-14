package com.programmergabut.katili_jiwo_adi_wiyono_takehome.base

/*
   Created by Katili Jiwo A.W. 11 January 2021
*/

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel: ViewModel() {
    companion object {
        const val SHOW_LOADING : Int = 1
        const val REMOVE_LOADING : Int = 2
    }
    val loading : MutableLiveData<Int> = MutableLiveData()
}