package com.programmergabut.katili_jiwo_adi_wiyono_takehome.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.R
import com.programmergabut.katili_jiwo_adi_wiyono_takehome.databinding.LayoutErrorBottomsheetBinding

abstract class BaseActivity<DB: ViewDataBinding, VM: ViewModel>(
    private val layout: Int,
    private val viewModelClass: Class<VM>?
): AppCompatActivity() {

    protected lateinit var binding : DB
    protected lateinit var viewModel: VM
    private lateinit var root : ViewGroup
    private lateinit var loader : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layout)
        binding.lifecycleOwner = this

        root = findViewById(android.R.id.content)
        loader = LayoutInflater.from(this).inflate(R.layout.layout_loader, null, false)

        viewModelClass?.let {
            viewModel = ViewModelProvider(this).get(it)
        }

        setListener()
    }

    protected open fun setListener(){
        (viewModel as BaseViewModel).loading.observe(this,{ status ->
            when(status){
                BaseViewModel.SHOW_LOADING -> loading(true)
                BaseViewModel.REMOVE_LOADING -> loading(false)
            }
        })
    }

    private fun loading(isShow : Boolean){
        root.removeView(loader)
        if(isShow){
            root.addView(loader)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return super.dispatchTouchEvent(ev)
    }

    protected fun hideSoftKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }


    protected fun showErrorBottomSheet(
        title: String = resources.getString(R.string.text_error_title),
        description: String = resources.getString(R.string.text_error_dsc),
        isCancelable: Boolean = true,
        isFinish: Boolean = false,
        callback: (() -> Unit)? = null
    ) {

        val dialogBinding = DataBindingUtil.inflate<LayoutErrorBottomsheetBinding>(
            layoutInflater, R.layout.layout_error_bottomsheet, null, true
        )
        val dialog = BottomSheetDialog(this)

        dialogBinding.apply {
            tvTitle.text = title
            tvDesc.text = description
        }

        dialog.apply {
            window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setCancelable(isCancelable)
            setContentView(dialogBinding.root)
            show()
        }

        dialogBinding.btnOk.setOnClickListener {
            if(isFinish)
                finish()

            callback?.invoke()
            dialog.dismiss()
        }
    }


}