package com.example.todoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.CurrentUserHolder
import com.example.todoapp.model.Activities
import com.example.todoapp.ui.TodoViewModel


class RecViewAdapter(private val viewModel: TodoViewModel) : RecyclerView.Adapter<RecViewAdapter.ViewHolder>() {
    private var data: List<Activities> = emptyList()
    private var onItemClickListener: OnItemClickListener? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var itemName: TextView = itemView.findViewById(R.id.textView7)
        var view: View = itemView.findViewById(R.id.view3) // Add reference to the view

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onItemClickListener?.onItemClick(position)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    fun setData(newData: List<Activities>) {
        data = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return ViewHolder(v)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val activity = data[position]
        holder.itemName.text = activity.activity

        if (activity.isDone == 1) {
            holder.view.setBackgroundResource(R.drawable.radiobuttonselectorselected)
        } else {
            holder.view.setBackgroundResource(R.drawable.radiobuttonselector)
        }

        holder.view.setOnClickListener {
            CurrentUserHolder.getCurrentUser()
                ?.let { it1 -> viewModel.updateItemStatus(it1, position) }
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}