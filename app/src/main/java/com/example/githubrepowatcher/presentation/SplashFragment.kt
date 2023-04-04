package com.example.githubrepowatcher.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.githubrepowatcher.R
import com.example.githubrepowatcher.databinding.FragmentSplashBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private lateinit var binding: FragmentSplashBinding

    private lateinit var mainViewModel: MainViewModel

    private lateinit var token: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity) {
            mainViewModel = context.mainViewModel
        } else {
            throw RuntimeException("$context must be MainActivity")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)
        showSplash()
    }

    private fun showSplash() {
        lifecycleScope.launch(Dispatchers.Main) {
            delay(1000)
            observeViewModel()
            mainViewModel.checkAuth()
        }
    }

    private fun observeViewModel() {
        mainViewModel.token.observe(viewLifecycleOwner) {
            token = it
        }
        mainViewModel.isAuth.observe(viewLifecycleOwner) {
            navigateToNextScreen(it)
        }
    }

    private fun navigateToNextScreen(isAuth: Boolean) {
        val action = if (isAuth) {
            SplashFragmentDirections.actionSplashFragmentToRepoFragment(token)
        } else {
            SplashFragmentDirections.actionSplashFragmentToAuthFragment()
        }
        findNavController().navigate(action)
    }
}