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
import com.example.githubrepowatcher.databinding.FragmentAuthBinding
import com.example.githubrepowatcher.presentation.SessionCallback
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthFragment : Fragment() {
    private var _binding: FragmentAuthBinding? = null
    private val binding: FragmentAuthBinding
        get() = _binding ?: throw RuntimeException("FragmentAuthBinding is null")

    private val authViewModel by lazy {
        ViewModelProvider(this)[AuthViewModel::class.java]
    }

    private lateinit var sessionCallback: SessionCallback

    override fun onAttach(context: Context) {
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
                authViewModel.hideError()
            }
        })
        binding.bSignIn.setOnClickListener {
            lifecycleScope.launch {
                val authToken = binding.etToken.text.toString()
                authViewModel.onSignButtonPressed(authToken)
                authViewModel.actions.first { action ->
                    when(action) {
                        is AuthViewModel.Action.ShowError -> {
                            showErrorDialog()
                        }
                        is AuthViewModel.Action.RouteToMain ->
                            sessionCallback.startSession(authToken)
                    }
                    true
                }
            }
        }
    }

    private fun observeViewModel() {
        authViewModel.userInfo.observe(viewLifecycleOwner) {
            Log.d("Username", it.login)
        }
        authViewModel.state.observe(viewLifecycleOwner) {
            if (it is AuthViewModel.State.InvalidInput) {
                showError()
            } else {
                hideError()
            }
        }
    }

    private fun showError() {
        with(binding) {
            tilToken.error = " "
            tilToken.isErrorEnabled = true
            tvHelperText.isVisible = true
        }
    }

    private fun hideError() {
        with(binding) {
            tilToken.error = null
            tilToken.isErrorEnabled = false
            tvHelperText.isVisible = false
        }
    }

    private fun showErrorDialog() {
        Toast.makeText(requireContext(), "Error Dialog", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(): Fragment {
            return AuthFragment()
        }
    }
}