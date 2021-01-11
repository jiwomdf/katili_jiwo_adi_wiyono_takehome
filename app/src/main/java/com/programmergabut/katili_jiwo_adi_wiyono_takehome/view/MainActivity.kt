package com.programmergabut.katili_jiwo_adi_wiyono_takehome.view

/*
    Created by Katili Jiwo A.W. 11 January 2021
 */

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.R
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.base.BaseActivity
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.base.BaseViewModel
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.databinding.ActivityMainBinding
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.view.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity @Inject constructor (
    private val userAdapter: UserAdapter
): BaseActivity<ActivityMainBinding, UserViewModel>(
    R.layout.activity_main,
    UserViewModel::class.java
) {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.fetchListUser("jiwomdf")
        setupRecyclerView()
    }

    override fun setListener() {
        super.setListener()

        viewModel.usersStatus.observe(this, { status ->

            when(status){
                BaseViewModel.SUCCESS -> {
                    userAdapter.listItem = viewModel.getUsers()
                    userAdapter.notifyDataSetChanged()
                }
                BaseViewModel.ERROR -> {
                    showErrorBottomSheet()
                }
            }

        })
    }

    private fun setupRecyclerView(){
        binding.rvGithubUser.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

}