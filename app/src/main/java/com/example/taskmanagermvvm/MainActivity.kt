package com.example.taskmanagermvvm

import com.example.taskmanagermvvm.core.base.BaseActivity
import com.example.taskmanagermvvm.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding, ActivityViewModel>() {
    override val viewModel: ActivityViewModel
        get() = TODO()
    override fun inflateViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
}