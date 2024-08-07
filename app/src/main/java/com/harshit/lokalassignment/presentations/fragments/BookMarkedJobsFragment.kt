package com.harshit.lokalassignment.presentations.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.harshit.lokalassignment.databinding.FragmentBookMarkedJobsBinding
import com.harshit.lokalassignment.presentations.adapters.JobListAdapter
import com.harshit.lokalassignment.presentations.viewmodels.BookMarkViewModel
import com.harshit.lokalassignment.utils.Constants
import com.harshit.lokalassignment.utils.SnackarEvent
import com.harshit.lokalassignment.utils.Snacker

class BookMarkedJobsFragment : Fragment(), JobDetailFragment.BookMarkChangeListener {

    private lateinit var bookMarkViewModel: BookMarkViewModel
    private val adapter = JobListAdapter()
    private var _binding: FragmentBookMarkedJobsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bookMarkViewModel = ViewModelProvider(requireActivity())[BookMarkViewModel::class.java]
        _binding = FragmentBookMarkedJobsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        bookMarkViewModel.getBookMarkedJobs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initViews() {
        binding.bookmarkJobRV.adapter = adapter
        binding.bookmarkJobRV.layoutManager = LinearLayoutManager(requireContext())
        adapter.onItemClick = { id ->
            val fragment = JobDetailFragment()
            val bundle = Bundle().apply {
                putString(Constants.JOB_KEY, Gson().toJson(id))
                putBoolean(Constants.IS_BOOKMARK, true)
            }
            fragment.arguments = bundle
            fragment.setOnChooseReasonListener(this)
            fragment.show(requireActivity().supportFragmentManager, fragment.tag)
        }
    }

    private fun initObservers() {
        bookMarkViewModel.bookMarkJobList.observe(viewLifecycleOwner) { jobs ->
            binding.bookmarkEmptyMessage.visibility =
                if (jobs.isNullOrEmpty()) View.VISIBLE else View.GONE
            adapter.submitList(jobs)
        }

        bookMarkViewModel.snackbarMessage.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { snackbarEvent ->
                val snacker = Snacker(binding.root, snackbarEvent.message)
                when (snackbarEvent) {
                    is SnackarEvent.Error -> {
                        binding.errorView.visibility = View.VISIBLE
                        snacker.error()
                    }

                    is SnackarEvent.Loading -> {
                        // Handle loading state if necessary
                    }

                    is SnackarEvent.Success -> {
                        snacker.success()
                    }
                }
            }
        }

        bookMarkViewModel.isLoading.observe(viewLifecycleOwner) {
            binding.bookmarkProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }

    override fun onChange() {
        bookMarkViewModel.getBookMarkedJobs()
    }
}