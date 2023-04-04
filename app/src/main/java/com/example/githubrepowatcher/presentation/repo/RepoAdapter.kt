package com.example.githubrepowatcher.presentation.repo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.githubrepowatcher.databinding.ItemRepoBinding
import com.example.githubrepowatcher.domain.models.Repo

class RepoAdapter : ListAdapter<Repo, RepoViewHolder>(RepoDiffCallback) {
    var onRepoClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val binding = ItemRepoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RepoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = getItem(position)
        with(holder.binding) {
            tvRepositoryName.text = repo.name
            tvLanguage.text = repo.language
            tvDescription.text = repo.description
            root.setOnClickListener { onRepoClick?.invoke(position) }
        }
    }
}