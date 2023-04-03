package com.example.githubrepowatcher.presentation.auth

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.githubrepowatcher.presentation.RepoWatcherApp
import com.example.githubrepowatcher.databinding.FragmentAuthBinding
import com.example.githubrepowatcher.presentation.SessionCallback
import com.example.githubrepowatcher.presentation.ViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthFragment : Fragment() {
    private var _binding: FragmentAuthBinding? = null
    private val binding: FragmentAuthBinding
        get() = _binding ?: throw RuntimeException("FragmentAuthBinding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val authViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AuthViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as RepoWatcherApp).component
    }

    private lateinit var sessionCallback: SessionCallback

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
        if (context is SessionCallback) {
            sessionCallback = context
        } else {
            throw RuntimeException("$context must implement SessionCallback")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupListeners() {
        binding.etToken.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                authViewModel.shouldHideError()
            }
        })
        binding.bSignIn.setOnClickListener {
            val authToken = binding.etToken.text.toString()
            with(authViewModel) {
                setAuthToken(authToken)
                onSignButtonPressed()
                lifecycleScope.launch {
                    actions.first { action ->
                        when (action) {
                            is AuthViewModel.Action.ShowError ->
                                showErrorDialog()
                            is AuthViewModel.Action.RouteToMain ->
                                sessionCallback.startSession(authToken)
                        }
                        true
                    }
                }
            }
        }
    }

    private fun observeViewModel() {
        authViewModel.userInfo.observe(viewLifecycleOwner) {
            Log.d("Username", it.login)
        }
        authViewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is AuthViewModel.State.Idle -> {
                    with(binding) {
                        binding.progressBar.isVisible = false
                        tilToken.error = null
                        tilToken.isErrorEnabled = false
                        tvHelperText.isVisible = false
                    }
                }
                is AuthViewModel.State.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is AuthViewModel.State.InvalidInput -> {
                    with(binding) {
                        binding.progressBar.isVisible = false
                        tilToken.error = " "
                        tilToken.isErrorEnabled = true
                        tvHelperText.isVisible = true
                    }
                }
            }
        }
    }

    // TODO: create dialog
    private fun showErrorDialog() {
        Toast.makeText(requireContext(), "Error Dialog", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(): Fragment {
            return AuthFragment()
        }
    }
}