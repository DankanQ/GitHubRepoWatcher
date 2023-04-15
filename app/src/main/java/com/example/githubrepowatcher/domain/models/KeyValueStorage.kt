package com.example.githubrepowatcher.domain.models

import com.example.githubrepowatcher.di.AppScope
import javax.inject.Inject

@AppScope
class KeyValueStorage @Inject constructor() {
    var authToken: String? = null
}
