package com.harshit.lokalassignment.presentations.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshit.lokalassignment.data.models.JobList
import com.harshit.lokalassignment.data.remote.JobsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.harshit.lokalassignment.data.models.Result
import com.harshit.lokalassignment.utils.Event
import com.harshit.lokalassignment.utils.SnackarEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import retrofit2.HttpException
import java.io.IOException

@HiltViewModel
class JobsViewModel @Inject constructor(private val repository: JobsRepository) : ViewModel() {
    private val _jobList = MutableLiveData<JobList?>()
    val jobList: LiveData<JobList?> = _jobList

    private val _snackbarMessage = MutableLiveData<Event<SnackarEvent>>()
    val snackbarMessage: LiveData<Event<SnackarEvent>> = _snackbarMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private fun getJobList(page: Int = 1) {
        _isLoading.value = true
        viewModelScope.launch {
            repository.getJobList(page).collectLatest{ result ->
                when(result){
                    is Result.Success -> {
                        _isLoading.value = false
                        val list = result.data
                        _jobList.value = list
                        _snackbarMessage.postValue(Event(SnackarEvent.Success("Job list loaded successfully")))
                    }
                    is Result.Error -> {
                        _isLoading.value = false
                        _snackbarMessage.postValue(Event(SnackarEvent.Error(result.message?:"Something went wrong")))
                    }
                    is Result.Loading -> {
                        _isLoading.value = true
                        _snackbarMessage.postValue(Event(SnackarEvent.Loading("Loading...")))
                    }
                }
            }
        }
    }

    init {
        getJobList()
    }

}
