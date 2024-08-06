package com.harshit.lokalassignment.presentations.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshit.lokalassignment.data.local.repositories.BookMarkRepository
import com.harshit.lokalassignment.data.models.Jobs
import com.harshit.lokalassignment.data.models.Result
import com.harshit.lokalassignment.utils.Event
import com.harshit.lokalassignment.utils.SnackarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(private val repository: BookMarkRepository) :
    ViewModel() {
    private val _bookMarkJobList = MutableLiveData<List<Jobs>?>()
    val bookMarkJobList: LiveData<List<Jobs>?> = _bookMarkJobList

    private val _snackbarMessage = MutableLiveData<Event<SnackarEvent>>()
    val snackbarMessage: LiveData<Event<SnackarEvent>> = _snackbarMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private fun getBookMarkedJobs() {
        viewModelScope.launch {
            repository.getBookMarkedJobs().collectLatest { result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.value = true
                        _snackbarMessage.postValue(Event(SnackarEvent.Loading("Loading...")))
                    }

                    is Result.Success -> {
                        _isLoading.value = false
                        _bookMarkJobList.value = result.data
                    }

                    is Result.Error -> {
                        _isLoading.value = false
                        _snackbarMessage.postValue(
                            Event(
                                SnackarEvent.Error(
                                    result.message ?: "Something went wrong"
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    private fun deleteJob(job: Jobs) {
        viewModelScope.launch {
            repository.deleteJob(job).collectLatest { result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.value = true
                        _snackbarMessage.postValue(Event(SnackarEvent.Loading("Loading...")))
                    }

                    is Result.Success -> {
                        _bookMarkJobList.value = _bookMarkJobList.value?.minus(job)
                        _isLoading.value = false
                        _snackbarMessage.postValue(Event(SnackarEvent.Success("Job deleted successfully")))
                    }

                    is Result.Error -> {
                        _isLoading.value = false
                        _snackbarMessage.postValue(
                            Event(
                                SnackarEvent.Error(
                                    result.message ?: "Something went wrong"
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    init {
        getBookMarkedJobs()
    }
}