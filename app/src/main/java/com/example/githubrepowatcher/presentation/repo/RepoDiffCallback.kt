package com.example.githubrepowatcher.presentation.repo

import androidx.recyclerview.widget.DiffUtil
import com.example.githubrepowatcher.domain.models.Repo

object RepoDiffCallback : DiffUtil.ItemCallback<Repo>() {
    override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
        return oldItem == newItem
    }
}