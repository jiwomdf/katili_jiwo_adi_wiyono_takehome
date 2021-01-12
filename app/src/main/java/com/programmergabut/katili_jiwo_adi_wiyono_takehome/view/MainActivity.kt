package com.programmergabut.katili_jiwo_adi_wiyono_takehome.view

/*
    Created by Katili Jiwo A.W. 11 January 2021
 */

import android.os.Bundle
import android.view.inputmethod.EditorInfo
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
    private val perPage = 10
    private var currPage = 1
    private var lastSearchedString = ""
    private var isAdapterLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupRecyclerView()
    }

    override fun setListener() {
        super.setListener()

        viewModel.usersStatus.observe(this, { status ->
            when(status){
                BaseViewModel.SUCCESS -> {
                    setAdapterLoading(false)
                    userAdapter.listItem.addAll(viewModel.getUsers())
                    userAdapter.notifyDataSetChanged()
                }
                BaseViewModel.ERROR -> {
                    setAdapterLoading(false)
                    showErrorBottomSheet()
                }
            }
        })

        binding.rvGithubUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastLayoutCompletePosition = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                val adapterReloadLength = userAdapter.listItem.size - 2

                if(lastSearchedString.isEmpty()){
                    showErrorBottomSheet(
                        getString(R.string.search_string_empty_title),
                        getString(R.string.search_string_empty_dsc),
                        isCancelable = true,
                        isFinish = false
                    )
                    return
                }

                if(!isAdapterLoading && linearLayoutManager != null && lastLayoutCompletePosition == adapterReloadLength){

                    setAdapterLoading(true)
                    currPage++
                    viewModel.fetchListUser(lastSearchedString, currPage, perPage)
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })

        binding.etSerch.setOnEditorActionListener{ _, keyCode, _ ->
            if (keyCode == EditorInfo.IME_ACTION_SEARCH) {
                lastSearchedString = binding.etSerch.text.toString().trim()
                hideSoftKeyboard()

                if(lastSearchedString.isEmpty()){
                    showErrorBottomSheet(
                        getString(R.string.search_string_empty_title),
                        getString(R.string.search_string_empty_dsc),
                        isCancelable = true,
                        isFinish = false)
                    return@setOnEditorActionListener false
                }

                viewModel.fetchListUser(lastSearchedString, currPage, perPage)
                return@setOnEditorActionListener true
            }
            false
        }

    }

    private fun setAdapterLoading(isLoading: Boolean) {
        if(currPage <= 1)
            return

        isAdapterLoading = isLoading

        if(isLoading){
            userAdapter.listItem.add(null)
            userAdapter.notifyItemInserted(userAdapter.listItem.size - 1)
        }
        else{
            userAdapter.listItem.removeAt(userAdapter.listItem.size - 1)
            userAdapter.notifyItemRemoved(userAdapter.listItem.size - 1)
        }
    }

    private fun setupRecyclerView(){
        binding.rvGithubUser.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
        }
    }

}