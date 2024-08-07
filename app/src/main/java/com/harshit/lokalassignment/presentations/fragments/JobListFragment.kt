package com.harshit.lokalassignment.presentations.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.harshit.lokalassignment.databinding.FragmentJobListBinding
import com.harshit.lokalassignment.presentations.adapters.JobListAdapter
import com.harshit.lokalassignment.presentations.viewmodels.JobsViewModel
import com.harshit.lokalassignment.utils.Constants
import com.harshit.lokalassignment.utils.SnackarEvent
import com.harshit.lokalassignment.utils.Snacker


class JobListFragment : Fragment() {
    private lateinit var jobListViewModel: JobsViewModel
    private val adapter = JobListAdapter()
    private var _binding: FragmentJobListBinding? = null
    private val binding get() = _binding!!
    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        jobListViewModel = ViewModelProvider(requireActivity())[JobsViewModel::class.java]
        _binding = FragmentJobListBinding.inflate(inflater, container, false)
        return binding.root
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
        binding.jobsRecyclerView.adapter = adapter
        binding.jobsRecyclerView.setHasFixedSize(true)
        binding.jobsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter.onItemClick = { job ->
            val fragment = JobDetailFragment()
            val bundle = Bundle()
            bundle.putString(Constants.JOB_KEY, Gson().toJson(job))
            bundle.putBoolean(Constants.IS_BOOKMARK, false)
            fragment.arguments = bundle
            fragment.show(requireActivity().supportFragmentManager, fragment.tag)
        }

        binding.jobsRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount =
                    (recyclerView.layoutManager as LinearLayoutManager).childCount
                val totalItemCount = (recyclerView.layoutManager as LinearLayoutManager).itemCount
                val firstVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

                if (!isLoading && (visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                    jobListViewModel.loadNextPage()
                }
            }
        })
    }

    private fun initObservers() {
        jobListViewModel.jobList.observe(viewLifecycleOwner) { jobs ->
            val list = jobs?.jobs?.filter { it.id != null }
            binding.jobsEmptyMessage.visibility =
                if (list.isNullOrEmpty()) View.VISIBLE else View.GONE
            adapter.submitList(list)
        }

        jobListViewModel.snackbarMessage.observe(viewLifecycleOwner) { event ->
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

        jobListViewModel.isLoading.observe(viewLifecycleOwner) {
            isLoading = it
            binding.jobsProgressBar.visibility = if (it) View.VISIBLE else View.GONE
        }
    }
}
