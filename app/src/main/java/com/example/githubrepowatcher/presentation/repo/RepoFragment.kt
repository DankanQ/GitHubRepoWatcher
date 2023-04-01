package com.example.githubrepowatcher.presentation.repo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.githubrepowatcher.databinding.FragmentRepoBinding
import com.example.githubrepowatcher.presentation.SessionCallback

class RepoFragment : Fragment() {
    private var _binding: FragmentRepoBinding? = null
    private val binding: FragmentRepoBinding
        get() = _binding ?: throw RuntimeException("FragmentRepoBinding is null")

    private lateinit var sessionCallback: SessionCallback

    private var authToken = UNDEFINED_AUTH_TOKEN

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is SessionCallback) {
            sessionCallback = context
        } else {
            throw RuntimeException("$context must implement SessionCallback")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()
    }

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

        binding.bLogout.setOnClickListener {
            sessionCallback.endSession()
        }

        binding.tvAuthToken.text = authToken
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        val args = requireArguments()
        if (args.containsKey(AUTH_TOKEN)) {
            authToken = args.getString(AUTH_TOKEN) ?: UNDEFINED_AUTH_TOKEN
        } else {
            throw RuntimeException("Args doesn't contain a AUTH_TOKEN key")
        }
    }

    companion object {
        private const val AUTH_TOKEN = "authToken"
        private const val UNDEFINED_AUTH_TOKEN = ""

        fun newInstance(authToken: String): Fragment {
            return RepoFragment().apply {
                arguments = Bundle().apply {
                    putString(AUTH_TOKEN, authToken)
                }
            }
        }
    }
}