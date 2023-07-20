package com.example.todoapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.adapter.RecViewAdapter
import com.example.todoapp.data.DatabaseService
import com.example.todoapp.databinding.FragmentTodoPageBinding
import com.example.todoapp.model.UiState
import kotlinx.coroutines.launch


class TodoPageFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecViewAdapter
    private val todoViewModel: TodoViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentTodoPageBinding.inflate(inflater, container, false)

        displayName(binding)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = RecViewAdapter(todoViewModel)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : RecViewAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                DatabaseService.getCurrentUser()
                    ?.let { todoViewModel.updateItemStatus(it,position) }
            }
        })

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {

        }

        lifecycleScope.launch {
            todoViewModel.activitiesState.collect { activities ->
                adapter.setData(activities)
            }
        }

        onClickAddItem(binding)
        println(DatabaseService.getCurrentUser())

        return binding.root
    }

    private fun displayName(binding: FragmentTodoPageBinding) {
        val currentUserFullName = DatabaseService.getCurrentUser()?.fullname
        val welcomeMessage = getString(R.string.welcome_message, currentUserFullName)
        binding.nameText.text = welcomeMessage
    }

    private fun onClickAddItem(binding: FragmentTodoPageBinding) {
        binding.additionButton.setOnClickListener {
            val textAdd = binding.taskToBeAdded.text.toString()

            lifecycleScope.launch {
                todoViewModel.addItemToRoomDatabase(textAdd)
                todoViewModel.additionState.collect { state ->
                    when (state) {
                        is UiState.Success -> {
                            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                        }
                        is UiState.Error -> {
                            Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
                        }
                        else -> {}
                    }
                }
            }
        }
    }

}