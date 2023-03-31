package com.example.githubrepowatcher.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.githubrepowatcher.R
import com.example.githubrepowatcher.databinding.FragmentRepoBinding
import com.example.githubrepowatcher.presentation.viewmodels.SessionViewModel

class RepoFragment : Fragment() {
    private var _binding: FragmentRepoBinding? = null
    private val binding: FragmentRepoBinding
        get() = _binding ?: throw RuntimeException("FragmentRepoBinding is null")

    private lateinit var sessionViewModel: SessionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentRepoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionViewModel = ViewModelProvider(this)[SessionViewModel::class.java]
        binding.tvAuthToken.text = sessionViewModel.getAuthToken()?.authToken ?: "EMPTY_AUTH_TOKEN"
        binding.bLogout.setOnClickListener {
            sessionViewModel.clearAuthToken()
            val authFragment = AuthFragment.newInstance()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, authFragment)
                .commit()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): Fragment {
            return RepoFragment()
        }
    }
}