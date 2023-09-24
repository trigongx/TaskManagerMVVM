package com.example.taskmanagermvvm.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<
        VB : ViewBinding,
        VM : ViewModel>
    : AppCompatActivity() {

    private lateinit var binding: VB
    protected abstract val viewModel: VM
    protected abstract fun inflateViewBinding(): VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflateViewBinding()
        setContentView(binding.root)
        initInternetConnection()
        initView()
        initViewModel()
        initListener()
    }

    open fun initListener() {}

    open fun initViewModel() {}

    open fun initView() {}

    open fun initInternetConnection() {}

}