package com.example.githubrepowatcher.presentation

interface SessionCallback {
    fun startSession(authToken: String)

    fun endSession()
}