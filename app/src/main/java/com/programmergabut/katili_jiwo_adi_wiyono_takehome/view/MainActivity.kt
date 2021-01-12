package com.programmergabut.katili_jiwo_adi_wiyono_takehome.view

/*
    Created by Katili Jiwo A.W. 11 January 2021
 */

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
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
    private var isHasReachBottomOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupRecyclerView()
    }

    override fun setListener() {
        super.setListener()

        viewModel.usersStatus.observe(this, { status ->
            when (status) {
                BaseViewModel.SUCCESS -> {
                    if(currPage == 1){
                        setAdapterLoading(true)
                    }
                    else if(currPage > 1){
                        setAdapterLoading(false)
                        isHasReachBottomOnce = false
                    }
                    userAdapter.listItem.addAll(viewModel.getUsers())
                    userAdapter.notifyDataSetChanged()
                }
                BaseViewModel.ERROR -> {
                    showErrorBottomSheet(
                        description = viewModel.getMessage(),
                        isCancelable = false
                    )
                }
                BaseViewModel.LIMIT -> {
                    showErrorBottomSheet(
                        description = viewModel.getMessage(),
                        isCancelable = false,
                        callback = {
                            setAdapterLoading(false)
                            isHasReachBottomOnce = false
                        }
                    )
                }
            }
        })

        binding.rvGithubUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (dy < 0 || dy == 0 || lastSearchedString.isEmpty())
                    return

                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisible = linearLayoutManager.findLastCompletelyVisibleItemPosition() + 2
                val adapterReloadLength = userAdapter.listItem.size
                val endHasBeenReached: Boolean = lastVisible >= adapterReloadLength

                Log.d(
                    "<TESTING>",
                    "$isHasReachBottomOnce $lastVisible $adapterReloadLength"
                )

                if (!isHasReachBottomOnce && endHasBeenReached) {
                    setAdapterLoading(true)
                    isHasReachBottomOnce = true
                    viewModel.fetchListUser(lastSearchedString, ++currPage, perPage)
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        })

        binding.etSerch.setOnEditorActionListener{ _, keyCode, _ ->
            if (keyCode == EditorInfo.IME_ACTION_SEARCH) {

                resetAdapterData()
                hideSoftKeyboard()
                lastSearchedString = binding.etSerch.text.toString().trim()

                if(lastSearchedString.isEmpty()){
                    showErrorBottomSheet(
                        getString(R.string.search_string_empty_title),
                        getString(R.string.search_string_empty_dsc)
                    )
                    return@setOnEditorActionListener false
                }

                viewModel.fetchListUser(lastSearchedString, currPage, perPage)
                return@setOnEditorActionListener true
            }
            false
        }

    }

    private fun resetAdapterData() {
        userAdapter.listItem.clear()
        userAdapter.notifyDataSetChanged()
        currPage = 1
        isHasReachBottomOnce = false
    }

    private fun setAdapterLoading(isLoading: Boolean) {
        if(currPage <= 1)
            return

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
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }

}