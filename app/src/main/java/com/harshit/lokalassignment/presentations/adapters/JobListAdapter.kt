package com.harshit.lokalassignment.presentations.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.harshit.lokalassignment.data.models.Jobs
import com.harshit.lokalassignment.databinding.JobItemViewBinding

class JobListAdapter : ListAdapter<Jobs, JobListAdapter.JobViewHolder>(DiffCallback) {

    var onItemClick: ((Jobs) -> Unit)? = null

    companion object DiffCallback : DiffUtil.ItemCallback<Jobs>() {
        override fun areItemsTheSame(oldItem: Jobs, newItem: Jobs): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Jobs, newItem: Jobs): Boolean {
            return oldItem == newItem
        }
    }

    inner class JobViewHolder(private val binding: JobItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(jobDetail: Jobs) {
            binding.jobDetail = jobDetail
            binding.executePendingBindings()
            binding.viewMore.setOnClickListener {
                onItemClick?.let { it1 -> it1(jobDetail) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobViewHolder {
        return JobViewHolder(
            JobItemViewBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: JobViewHolder, position: Int) {
        val jobDetail = getItem(position)
        holder.bind(jobDetail)
    }
}
