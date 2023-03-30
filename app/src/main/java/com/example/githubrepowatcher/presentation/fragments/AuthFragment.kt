package com.example.githubrepowatcher.presentation.fragments

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
import com.example.githubrepowatcher.R
import com.example.githubrepowatcher.databinding.FragmentAuthBinding
import com.example.githubrepowatcher.domain.models.KeyValueStorage
import com.example.githubrepowatcher.presentation.viewmodels.SessionViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AuthFragment : Fragment() {
    private var _binding: FragmentAuthBinding? = null
    private val binding: FragmentAuthBinding
        get() = _binding ?: throw RuntimeException("FragmentAuthBinding is null")

    private lateinit var sessionViewModel: SessionViewModel

    private val authToken = "asd123"

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
        sessionViewModel = ViewModelProvider(this)[SessionViewModel::class.java]

        if (sessionViewModel.getAuthToken() != null) {
            Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
            sessionViewModel.saveAuthToken(KeyValueStorage(authToken))
            val repoFragment = RepoFragment.newInstance()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, repoFragment)
                .commit()
        } else {
            Toast.makeText(requireContext(), "Let's sign in", Toast.LENGTH_SHORT).show()
        }


        val originalPaddingTop = binding.etToken.paddingTop
        binding.etToken.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                binding.tilToken.error = null
                binding.tilToken.isErrorEnabled = false
                binding.tvHelperText.isVisible = false
                binding.etToken.setPadding(
                    binding.etToken.paddingLeft,
                    originalPaddingTop,
                    binding.etToken.paddingRight,
                    binding.etToken.paddingBottom
                )
            }
        })
        binding.bSignIn.setOnClickListener {
            lifecycleScope.launch(Dispatchers.Main) {
                delay(1000)
                Log.d("SignIn", "Loading...")
                if (binding.etToken.text.toString() == authToken) {
                    Toast.makeText(requireContext(), "Success", Toast.LENGTH_SHORT).show()
                    sessionViewModel.saveAuthToken(KeyValueStorage(authToken))
                    val repoFragment = RepoFragment.newInstance()
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, repoFragment)
                        .commit()
                } else {
                    Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                    binding.tilToken.error = " "
                    binding.tilToken.isErrorEnabled = true
                    binding.tvHelperText.isVisible = true
                    binding.etToken.setPadding(
                        binding.etToken.paddingLeft,
                        (originalPaddingTop / 1.5).toInt(),
                        binding.etToken.paddingRight,
                        binding.etToken.paddingBottom
                    )
                }
                Log.d("SignIn", "Complete")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): Fragment {
            return AuthFragment()
        }
    }
}