package com.harshit.lokalassignment

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.harshit.lokalassignment.presentations.viewmodels.JobsViewModel
import com.harshit.lokalassignment.utils.Snacker
import com.harshit.lokalassignment.utils.SnackarEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var textViewJobList: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var jobsViewModel: JobsViewModel
    private lateinit var rootView: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        rootView = findViewById(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(rootView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        jobsViewModel = ViewModelProvider(this)[JobsViewModel::class.java]
        textViewJobList = findViewById(R.id.textViewJobList)
        progressBar = findViewById(R.id.progress_circular)
        jobsViewModel.snackbarMessage.observe(this) { event ->
            event.getContentIfNotHandled()?.let { snackbarEvent ->
                when (snackbarEvent) {
                    is SnackarEvent.Success -> {
                        Snacker(rootView, snackbarEvent.message).success()
                    }

                    is SnackarEvent.Error -> {
                        Snacker(rootView, snackbarEvent.message).error()
                    }

                    is SnackarEvent.Loading -> {
                        Snacker(rootView, snackbarEvent.message).warning()
                    }
                }
            }
        }

        jobsViewModel.jobList.observe(this) {
            textViewJobList.text = it?.jobs.toString()
        }

        jobsViewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }
}