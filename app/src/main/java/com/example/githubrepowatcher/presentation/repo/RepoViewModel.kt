package com.example.githubrepowatcher.presentation.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubrepowatcher.domain.models.Repo
import com.example.githubrepowatcher.domain.usecases.GetRepositoriesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepoViewModel @Inject constructor(
    private val getRepositoriesUseCase: GetRepositoriesUseCase,
) : ViewModel() {
    private val _repositories = MutableLiveData<List<Repo>>()
    val repositories: LiveData<List<Repo>> = _repositories

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _repositories.postValue(
                getRepositoriesUseCase.invoke()
            )
        }
    }
}