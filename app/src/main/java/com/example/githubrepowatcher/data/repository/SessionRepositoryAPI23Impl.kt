package com.example.githubrepowatcher.data.repository

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.githubrepowatcher.data.sessionmanagers.SessionManagerAPI23
import com.example.githubrepowatcher.domain.models.KeyValueStorage
import com.example.githubrepowatcher.domain.repository.SessionRepository
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

@RequiresApi(Build.VERSION_CODES.M)
class SessionRepositoryAPI23Impl(
    private val applicationContext: Context,
) : SessionRepository {

    private var sessionManager = SessionManagerAPI23()

    override fun saveAuthToken(keyValueStorage: KeyValueStorage) {
        val bytes = keyValueStorage.authToken!!.encodeToByteArray()
        val file = File(applicationContext.filesDir, "keystore.jks")
        if (!file.exists()) {
            file.createNewFile()
        }
        val fos = FileOutputStream(file)
        sessionManager.encrypt(bytes = bytes, outputStream = fos).decodeToString()
    }

    override fun getAuthToken(): KeyValueStorage? {
        val file = File(applicationContext.filesDir, "keystore.jks")
        if (file.exists()) {
            val authToken = sessionManager.decrypt(
                inputStream = FileInputStream(file)
            ).decodeToString()
            return KeyValueStorage(authToken)
        } else {
            return null
        }
    }

    override fun clearAuthToken() {
        val file = File(applicationContext.filesDir, "keystore.jks")
        if (file.exists()) {
            file.delete()
        }
    }
}