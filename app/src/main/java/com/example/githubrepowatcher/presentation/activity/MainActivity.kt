package com.example.githubrepowatcher.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.githubrepowatcher.R
import com.example.githubrepowatcher.presentation.viewmodels.SessionViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var sessionViewModel: SessionViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sessionViewModel = ViewModelProvider(this)[SessionViewModel::class.java]
    }
}