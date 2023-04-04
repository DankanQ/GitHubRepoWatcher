package com.example.githubrepowatcher.presentation.repo

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.githubrepowatcher.R
import com.example.githubrepowatcher.databinding.FragmentRepoBinding
import com.example.githubrepowatcher.presentation.RepoWatcherApp
import com.example.githubrepowatcher.presentation.SessionCallback
import com.example.githubrepowatcher.presentation.ViewModelFactory
import javax.inject.Inject

class RepoFragment : Fragment(R.layout.fragment_repo) {
    private lateinit var binding: FragmentRepoBinding

    private val args by navArgs<RepoFragmentArgs>()

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val repoViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[RepoViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as RepoWatcherApp).component
            .tokenComponentFactory()
            .create(args.token)
    }

    private lateinit var sessionCallback: SessionCallback

    private val repoAdapter = RepoAdapter()

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
        if (context is SessionCallback) {
            sessionCallback = context
        } else {
            throw RuntimeException("$context must implement SessionCallback")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepoBinding.bind(view)

        binding.tbRepo.ibLogout.setOnClickListener {
            sessionCallback.endSession()
        }

        binding.rvRepo.adapter = repoAdapter

        observeViewModel()
        repoViewModel.loadRepositories()
    }

    private fun observeViewModel() {
        repoViewModel.repositories.observe(viewLifecycleOwner) {
            repoAdapter.submitList(it)
            Log.d("Repo", it.toString())
        }
    }
}