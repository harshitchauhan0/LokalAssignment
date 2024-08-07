package com.harshit.lokalassignment.presentations.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.harshit.lokalassignment.data.local.repositories.JobDetailRepository
import com.harshit.lokalassignment.data.models.Jobs
import com.harshit.lokalassignment.data.models.Result
import com.harshit.lokalassignment.utils.Event
import com.harshit.lokalassignment.utils.SnackarEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JobDetailViewModel @Inject constructor(private val repository: JobDetailRepository) :
    ViewModel() {

    private val _snackbarMessage = MutableLiveData<Event<SnackarEvent>>()
    val snackbarMessage: LiveData<Event<SnackarEvent>> = _snackbarMessage

     fun removeJobFromBookMark(job: Jobs) {
        viewModelScope.launch {
            repository.deleteJob(job).collectLatest { result ->
                when (result) {
                    is Result.Loading -> {
//                        Handle Loading
                    }

                    is Result.Success -> {
                        _snackbarMessage.postValue(Event(SnackarEvent.Success("Job Deleted successfully")))
                    }

                    is Result.Error -> {
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

     fun insertJob(job: Jobs) {
        viewModelScope.launch {
            repository.insertJob(job).collectLatest { result ->
                when (result) {
                    is Result.Loading -> {
//                        Handle Loading
                    }

                    is Result.Success -> {
                        _snackbarMessage.postValue(Event(SnackarEvent.Success("Job BookMarked successfully")))
                    }

                    is Result.Error -> {
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

}