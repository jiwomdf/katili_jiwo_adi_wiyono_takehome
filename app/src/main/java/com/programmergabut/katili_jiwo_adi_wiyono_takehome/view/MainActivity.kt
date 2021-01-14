package com.programmergabut.katili_jiwo_adi_wiyono_takehome.view

/*
   Created by Katili Jiwo A.W. 11 January 2021
*/

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.R
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.base.BaseActivity
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.databinding.ActivityMainBinding
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.view.adapter.FooterAdapter
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.view.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding, UserViewModel>(
    R.layout.activity_main,
    UserViewModel::class.java
) {

    @Inject lateinit var userAdapter: UserAdapter
    private var lastErrMsg = ""
    private var isFromSearching = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupRecyclerView()
    }

    override fun setListener() {
        super.setListener()

        viewModel.users.observe(this, { result ->
            userAdapter.submitData(lifecycle, result)
        })

        userAdapter.addLoadStateListener { combinedLoadStates ->
            if(combinedLoadStates.source.refresh is LoadState.NotLoading &&
                combinedLoadStates.append.endOfPaginationReached &&
                userAdapter.itemCount < 1){
                setContentComponentVisibility(isRvGithubVisible = false)
                if(lastErrMsg.isEmpty())
                    binding.tvInfo.text = getString(R.string.user_not_found)
                else
                    binding.tvInfo.text = lastErrMsg
            }
            else{
                setContentComponentVisibility(isRvGithubVisible = true)
            }

            when (combinedLoadStates.source.refresh) {
                is LoadState.Loading -> showLoading(true)
                is LoadState.NotLoading -> {
                    showLoading(false)
                    isFromSearching = false
                    if(userAdapter.itemCount < 1)
                        lastErrMsg = ""
                }
                is LoadState.Error -> {
                    resetData()
                    showSearchError(combinedLoadStates.source.refresh)
                    lastErrMsg = getErrorMsg(combinedLoadStates.source.refresh)
                }
            }
        }

        binding.etSearch.setOnEditorActionListener{ _, keyCode, _ ->
            if (keyCode == EditorInfo.IME_ACTION_SEARCH) {
                val searchString = binding.etSearch.text.toString().trim()
                if(searchString.isEmpty()){
                    showErrorBottomSheet(getString(R.string.search_string_empty_title), getString(R.string.search_string_empty_dsc))
                    return@setOnEditorActionListener false
                }
                resetData()
                hideSoftKeyboard()
                viewModel.searchPhoto(searchString)
                isFromSearching = true
                return@setOnEditorActionListener true
            }
            false
        }
    }

    private fun resetData() {
        userAdapter.submitData(lifecycle, PagingData.empty())
        userAdapter.notifyDataSetChanged()
    }

    private fun setupRecyclerView(){
        binding.rvGithubUser.apply {
            adapter = userAdapter.withLoadStateHeaderAndFooter(
                header = FooterAdapter(
                    { userAdapter.retry() },
                    { loadState -> showScrollError(loadState) }
                ),
                footer = FooterAdapter(
                    { userAdapter.retry() },
                    { loadState -> showScrollError(loadState) }
                )
            )
            layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.VERTICAL,
                false
            )
            setHasFixedSize(true)
        }
    }

    private fun showSearchError(loadState: LoadState){
        try {
            when (val err = (loadState as LoadState.Error).error) {
                is HttpException -> {
                    when (err.code()) {
                        403 -> showErrorBottomSheet(title = getString(R.string.error_403_title),
                            description = getString(R.string.error_403_dsc))
                        else -> showErrorBottomSheet(description = err.message.toString(), isCancelable = true,)
                    }
                }
                is IOException -> showErrorBottomSheet(title = getString(R.string.error_unknown_host_connection_title),
                    description = getString(R.string.error_unknown_host_connection_dsc))
                else -> showErrorBottomSheet()
            }.also {
                showLoading(false)
            }
        }
        catch (ex: Exception){
            showErrorBottomSheet()
        }
    }

    private fun showScrollError(loadState: LoadState) {
        if(isFromSearching)
            return
        try {
            when (val err = (loadState as LoadState.Error).error) {
                is HttpException -> {
                    when (err.code()) {
                        403 -> Toast.makeText(this, getString(R.string.error_403_dsc), Toast.LENGTH_SHORT).show()
                        else -> Toast.makeText(this, err.message().toString(), Toast.LENGTH_SHORT).show()
                    }
                }
                is IOException -> Toast.makeText(this, getString(R.string.error_unknown_host_connection_dsc), Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, resources.getString(R.string.text_error_title), Toast.LENGTH_SHORT).show()
            }
        }
        catch (ex: Exception){
            Toast.makeText(this, resources.getString(R.string.text_error_title), Toast.LENGTH_SHORT).show()
        }
    }

    private fun getErrorMsg(loadState: LoadState): String {
        return try {
            when (val err = (loadState as LoadState.Error).error) {
                is HttpException -> {
                    when (err.code()) {
                        403 -> getString(R.string.error_403_dsc)
                        else -> err.message().toString()
                    }
                }
                is IOException -> getString(R.string.error_unknown_host_connection_dsc)
                else -> resources.getString(R.string.text_error_title)
            }
        } catch (ex: Exception){
            resources.getString(R.string.text_error_title)
        }
    }

    private fun setContentComponentVisibility(isRvGithubVisible: Boolean){
        if(isRvGithubVisible){
            binding.rvGithubUser.isVisible = true
            binding.tvInfo.isVisible = false
        }
        else{
            binding.rvGithubUser.isVisible = false
            binding.tvInfo.isVisible = true
        }
    }

}