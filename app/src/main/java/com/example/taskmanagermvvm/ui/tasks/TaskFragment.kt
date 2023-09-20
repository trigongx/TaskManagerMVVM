package com.example.taskmanagermvvm.ui.tasks

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.taskmanagermvvm.R
import com.example.taskmanagermvvm.databinding.FragmentTasksBinding
import com.example.taskmanagermvvm.model.TaskModel
import com.example.taskmanagermvvm.ui.tasks.adapter.TaskAdapter

class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTasksBinding
    private lateinit var viewModel: TaskViewModel
    private val adapter = TaskAdapter(this::deleteTask)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTasksBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(requireActivity())[TaskViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        initView()
        initListener()

    }

    private fun getData() {
        setFragmentResultListener("task_key") { _, bundle ->
            bundle.getString("bundle_key")?.let { viewModel.addTask(it) }
        }
    }

    private fun initView() {
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

    private fun deleteTask(position:Int) {
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.delete_task))
            .setMessage(getString(R.string.are_you_sure_you_want_to_delete_this_task))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                viewModel.deleteTask(position)
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

}