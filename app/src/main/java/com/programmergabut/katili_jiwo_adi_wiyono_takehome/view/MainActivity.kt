package com.programmergabut.katili_jiwo_adi_wiyono_takehome.view

/*
    Created by Katili Jiwo A.W. 11 January 2021
 */

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.R
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.base.BaseActivity
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.base.BaseViewModel
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.databinding.ActivityMainBinding
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.view.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding, UserViewModel>(
    R.layout.activity_main,
    UserViewModel::class.java
) {

    @Inject lateinit var userAdapter: UserAdapter
    private val perPage = "10"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        binding.etSerch.setOnEditorActionListener{ _, keyCode, _ ->
            if (keyCode == EditorInfo.IME_ACTION_SEARCH) {
                val searchString = binding.etSerch.text.toString()

                if(searchString.isNullOrEmpty()){
                    showErrorBottomSheet(
                        getString(R.string.search_string_empty_title),
                        getString(R.string.search_string_empty_dsc),
                        isCancelable = true,
                        isFinish = false
                    )
                    return@setOnEditorActionListener false
                }

                viewModel.fetchListUser(searchString.trim(), "1", perPage)
                return@setOnEditorActionListener true
            }
            false
        }

        binding.rvGithubUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (dy > 0) {
                    print("MANTAP")
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })
    }

    private fun setupRecyclerView(){
        binding.rvGithubUser.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

}