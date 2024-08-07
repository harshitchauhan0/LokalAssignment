package com.harshit.lokalassignment.presentations.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshit.lokalassignment.data.models.JobList
import com.harshit.lokalassignment.data.models.Result
import com.harshit.lokalassignment.data.remote.JobsRepository
import com.harshit.lokalassignment.utils.Event
import com.harshit.lokalassignment.utils.SnackarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobsViewModel @Inject constructor(private val repository: JobsRepository) : ViewModel() {
    private val _jobList = MutableLiveData<JobList?>()
    val jobList: LiveData<JobList?> = _jobList

    private val _snackbarMessage = MutableLiveData<Event<SnackarEvent>>()
    val snackbarMessage: LiveData<Event<SnackarEvent>> = _snackbarMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var currentPage = 1
    private var isLastPage = false

    private fun getJobList(page: Int) {
        viewModelScope.launch {
            repository.getJobList(page).collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        _isLoading.value = false
                        if (page == 1) {
                            _jobList.value = result.data
                        } else {
                            val lastList = _jobList.value ?: JobList(emptyList())
                            val newJobs = result.data?.jobs ?: emptyList()
                            val updatedList = lastList.jobs?.plus(newJobs)
                            _jobList.value = JobList(updatedList)
                        }
                        _snackbarMessage.postValue(Event(SnackarEvent.Success("Job List Fetched!")))
                        isLastPage = result.data?.jobs?.isEmpty() ?: false
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

                    is Result.Loading -> {
                        _isLoading.value = true
                        _snackbarMessage.postValue(Event(SnackarEvent.Loading("Loading...")))
                    }
                }
            }
        }
    }

    fun loadNextPage() {
        if (!isLastPage && !isLoading.value!!) {
            currentPage++
            getJobList(currentPage)
        }
    }

    init {
        getJobList(currentPage)
    }
}

