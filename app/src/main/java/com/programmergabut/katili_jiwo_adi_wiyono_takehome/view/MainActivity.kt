package com.programmergabut.katili_jiwo_adi_wiyono_takehome.view

/*
    Created by Katili Jiwo A.W. 11 January 2021
 */

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.R
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.base.BaseActivity
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.databinding.ActivityMainBinding
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.view.adapter.FooterAdapter
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.view.adapter.UserrAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding, UserViewModel>(
    R.layout.activity_main,
    UserViewModel::class.java
) {

    //@Inject lateinit var userAdapter: UserAdapter
    @Inject lateinit var userrAdapter: UserrAdapter
    private val perPage = 10
    private var currPage = 1
    private var lastSearchedString = ""
    private var isHasReachBottomOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setupRecyclerView()
        setupRecyclerView2()
    }

    override fun setListener() {
        super.setListener()

        viewModel.userss.observe(this, { result ->
            userrAdapter.submitData(lifecycle, result)
        })

        userrAdapter.addLoadStateListener { loadState ->

            when(loadState.source.refresh){
                is LoadState.Loading -> {
                    showLoading(true)
                }
                is LoadState.NotLoading -> {
                    showLoading(false)
                }
                is LoadState.Error -> {
                    showErrorBottomSheet{
                        userrAdapter.retry()
                    }
                }
            }

            if(loadState.source.refresh is LoadState.NotLoading &&
                loadState.append.endOfPaginationReached &&
                    userrAdapter.itemCount < 1){

                binding.rvGithubUser.isVisible = false
                binding.tvInfo.isVisible = true
            }
            else{
                binding.rvGithubUser.isVisible = true
                binding.tvInfo.isVisible = false
            }

        }

        /* viewModel.usersStatus.observe(this, { status ->
            when (status) {
                BaseViewModel.SUCCESS -> {
                    if(currPage > 1){
                        setAdapterLoading(false)
                    }
                    userAdapter.listItem.addAll(viewModel.getUsers())
                    userAdapter.notifyDataSetChanged()
                }
                BaseViewModel.ERROR -> {
                    showErrorBottomSheet(
                        description = viewModel.getMessage(),
                        isCancelable = false,
                        callback = {
                            if(currPage > 1){
                                setAdapterLoading(false)
                                --currPage
                                //Log.d("<TESTING>", "LIMIT")
                            }
                        }
                    )
                }
                BaseViewModel.LIMIT -> {
                    showErrorBottomSheet(
                        description = viewModel.getMessage(),
                        isCancelable = false,
                         callback = {
                             if(currPage > 1){
                                 setAdapterLoading(false)
                                 --currPage
                                 //Log.d("<TESTING>", "LIMIT")
                             }
                         }
                    )
                }
            }
        }) */

        /* binding.rvGithubUser.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (dy < 0 || dy == 0 || lastSearchedString.isEmpty())
                    return

                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleViewHolder = linearLayoutManager.findLastVisibleItemPosition() + 1
                val hasReachedLastTwoViewHolder: Boolean = lastVisibleViewHolder >= userAdapter.listItem.size

                //if(hasReachedLastTwoViewHolder)
                    //Log.d("<TESTING>", "$isHasReachBottomOnce $hasReachedLastTwoViewHolder $lastVisibleViewHolder ${userAdapter.listItem.size} $currPage")

                if (!isHasReachBottomOnce && hasReachedLastTwoViewHolder) {

                    if(lastVisibleViewHolder % 2 != 0)
                        Log.d("<TESTING>", "$isHasReachBottomOnce $hasReachedLastTwoViewHolder $lastVisibleViewHolder ${userAdapter.listItem.size} $currPage")
                    
                    setAdapterLoading(true)
                    viewModel.fetchListUser(lastSearchedString, ++currPage, perPage)
                }

                super.onScrolled(recyclerView, dx, dy)
            }
        }) */

        binding.etSerch.setOnEditorActionListener{ _, keyCode, _ ->
            if (keyCode == EditorInfo.IME_ACTION_SEARCH) {

                val searchString = binding.etSerch.text.toString().trim()

                if(searchString.isEmpty()){
                    showErrorBottomSheet(
                        getString(R.string.search_string_empty_title),
                        getString(R.string.search_string_empty_dsc)
                    )
                    return@setOnEditorActionListener false
                }

                if((searchString == lastSearchedString) && userrAdapter.itemCount > 0 ){
                    hideSoftKeyboard()
                    return@setOnEditorActionListener false
                }

                resetData()
                hideSoftKeyboard()
                lastSearchedString = searchString

                viewModel.searchPhoto(lastSearchedString)
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun resetData() {
        userrAdapter.submitData(lifecycle, PagingData.empty())
        userrAdapter.notifyDataSetChanged()
        currPage = 1
    }

    /* private fun setAdapterLoading(isLoading: Boolean) {
        isHasReachBottomOnce = isLoading
        if(isLoading){
            userAdapter.listItem.add(null)
            userAdapter.notifyItemInserted(userAdapter.listItem.size - 1)
        }
        else{
            userAdapter.listItem.removeAt(userAdapter.listItem.size - 1)
            userAdapter.notifyItemRemoved(userAdapter.listItem.size - 1)
        }
    } */

    /* private fun setupRecyclerView(){
         binding.rvGithubUser.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
        }
    }*/

    private fun setupRecyclerView2(){

        binding.rvGithubUser.apply {
            adapter = userrAdapter.withLoadStateHeaderAndFooter(
                header = FooterAdapter {
                    userrAdapter.retry()
                },
                footer = FooterAdapter {
                    userrAdapter.retry()
                }
            )
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.VERTICAL,
                false,
            )
            setHasFixedSize(true)
        }
    }

}