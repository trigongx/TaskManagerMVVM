package com.example.taskmanagermvvm.presentation.tasks

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.taskmanagermvvm.R
import com.example.taskmanagermvvm.core.base.BaseFragment
import com.example.taskmanagermvvm.data.model.TaskModel
import com.example.taskmanagermvvm.databinding.FragmentTasksBinding
import com.example.taskmanagermvvm.presentation.tasks.adapter.TaskAdapter
import com.example.taskmanagermvvm.utils.Constants.KEY_ADD_TASK
import com.example.taskmanagermvvm.utils.Constants.KEY_DETAIL_TO_MAIN
import com.example.taskmanagermvvm.utils.Constants.KEY_SET_TASK
import com.example.taskmanagermvvm.utils.Constants.KEY_MAIN_TO_DETAIL
import com.example.taskmanagermvvm.utils.Constants.KEY_UPDATE_TASK

class TaskFragment : BaseFragment<FragmentTasksBinding, TaskViewModel>() {

    private val adapter = TaskAdapter(this::onDoneClick, this::onClickItem, this::onLongClickItem)
    override val viewModel: TaskViewModel
        get() = ViewModelProvider(this)[TaskViewModel::class.java]

    override fun inflateViewBinding(): FragmentTasksBinding =
        FragmentTasksBinding.inflate(layoutInflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        initListener()
        initMenu()

    }
    private fun initMenu() {
        val menu: MenuHost = requireActivity()
        menu.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.main_menu, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.show_done_task -> {
                        viewModel.doSortedList(TaskStatus.DONE)
                        return true
                    }

                    R.id.show_not_done_task -> {
                        viewModel.doSortedList(TaskStatus.NOT_DONE)
                        return true
                    }

                    R.id.show_all_task -> {
                        viewModel.doSortedList(TaskStatus.ALL)
                        return true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun getData() {
        setFragmentResultListener(KEY_DETAIL_TO_MAIN) { _, bundle ->
            bundle.getString(KEY_ADD_TASK)?.let { viewModel.addNewTask(it) }
            bundle.getSerializable(KEY_UPDATE_TASK)?.let { viewModel.updateTask(it as TaskModel) }
        }
    }

    override fun initView() {
        viewModel.liveData.observe(viewLifecycleOwner) { list ->
            adapter.addData(list)
            binding.rvTasks.adapter = adapter
        }
    }

    private fun initListener() {
        binding.btnAddTask.setOnClickListener {
            findNavController().navigate(R.id.addTaskFragment)
        }
    }

    private fun onLongClickItem(task: TaskModel) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.delete_task))
            .setMessage(getString(R.string.are_you_sure_you_want_to_delete_this_task))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                viewModel.deleteTask(task)
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun onDoneClick(task: TaskModel) {
        viewModel.setTaskDone(task)
    }

    private fun onClickItem(task: TaskModel) {
        setFragmentResult(KEY_MAIN_TO_DETAIL, bundleOf(KEY_SET_TASK to task))
        findNavController().navigate(R.id.addTaskFragment)
    }

}