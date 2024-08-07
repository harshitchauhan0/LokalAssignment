package com.harshit.lokalassignment.presentations.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import com.harshit.lokalassignment.R
import com.harshit.lokalassignment.data.models.Jobs
import com.harshit.lokalassignment.databinding.FragmentJobDetailBinding
import com.harshit.lokalassignment.presentations.viewmodels.JobDetailViewModel
import com.harshit.lokalassignment.utils.Constants
import com.harshit.lokalassignment.utils.SnackarEvent
import com.harshit.lokalassignment.utils.Snacker

class JobDetailFragment : BottomSheetDialogFragment() {
    private lateinit var jobDetailViewModel: JobDetailViewModel
    private lateinit var binding: FragmentJobDetailBinding
    private lateinit var job: Jobs

    interface BookMarkChangeListener {
        fun onChange()
    }

    private var listener: BookMarkChangeListener? = null

    fun setOnChooseReasonListener(listener: BookMarkChangeListener) {
        this.listener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_job_detail, container, false)
        jobDetailViewModel = ViewModelProvider(requireActivity())[JobDetailViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initClickListeners()
        initSnackBar()
    }

    private fun initSnackBar() {
        jobDetailViewModel.snackbarMessage.observe(this) { event ->
            event.getContentIfNotHandled()?.let { snackarEvent ->
                val snackBar = Snacker(binding.root, snackarEvent.message)
                when (snackarEvent) {
                    is SnackarEvent.Success -> {
                        changeButtonVisibility()
                        snackBar.success()
                    }

                    is SnackarEvent.Loading -> {
//                        Handling Loading
                    }

                    is SnackarEvent.Error -> {
                        snackBar.error()
                    }
                }
            }
        }
    }


    private fun changeButtonVisibility() {
        binding.addJobToBookMark.isVisible = !binding.addJobToBookMark.isVisible
        binding.removeFromBookMark.isVisible = !binding.removeFromBookMark.isVisible
    }

    private fun initClickListeners() {
        binding.addJobToBookMark.setOnClickListener {
            addToBookMark()
        }

        binding.removeFromBookMark.setOnClickListener {
            removeBookMark()
        }

        binding.contact.setOnClickListener {
            openLink(job.contactPreference?.whatsappLink ?: "")
        }
    }

    private fun openLink(url: String) {
        if (url.isEmpty()) return
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun removeBookMark() {
        jobDetailViewModel.removeJobFromBookMark(job)
        listener?.onChange()
        dismiss()
    }

    private fun addToBookMark() {
        jobDetailViewModel.insertJob(job)
    }

    private fun initViews() {
        val jobString = arguments?.getString(Constants.JOB_KEY)
        val isBookMarked = arguments?.getBoolean(Constants.IS_BOOKMARK) ?: false

        job = Gson().fromJson(jobString, Jobs::class.java)
        binding.jobdetail = job

        if (isBookMarked) {
            binding.addJobToBookMark.isVisible = false
        } else {
            binding.removeFromBookMark.isVisible = false
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupFullHeight(bottomSheetDialog)
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet =
            bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as? FrameLayout
        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)
            val layoutParams = it.layoutParams
            layoutParams.height = getWindowHeight()
            it.layoutParams = layoutParams
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        (context as? Activity)?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }
}
