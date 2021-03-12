package com.project.myapplication.ui.category

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.project.myapplication.R
import com.project.myapplication.databinding.CategoryDialogBinding

class CategoryBottomSheetDialog: BottomSheetDialogFragment() {

    companion object {
        private const val TAG = "CategoryBottomSheetDialog"
        fun showDialog(fragmentManager: FragmentManager, listener: CategoryBottomSheetDialogListener){
            if(fragmentManager.findFragmentByTag(TAG) == null){
                CategoryBottomSheetDialog().apply {
                    dialogListener = listener
                }.show(fragmentManager, TAG)
            }
        }
    }

    private var dialogListener: CategoryBottomSheetDialogListener?= null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val binding = CategoryDialogBinding.inflate(LayoutInflater.from(context))
        dialog.setContentView(binding.root)
        binding.popularTextView.setOnClickListener {
            dialogListener?.onClickedPopularMovies()
            dismissAllowingStateLoss()
        }
        binding.topRatedTextView.setOnClickListener {
            dialogListener?.onClickedTopRatedMovies()
            dismissAllowingStateLoss()
        }
        binding.nowPlayingTextView.setOnClickListener {
            dialogListener?.onClickedNowPlayingMovies()
            dismissAllowingStateLoss()
        }
        binding.upcomingTextView.setOnClickListener {
            dialogListener?.onClickedUpComingMovies()
            dismissAllowingStateLoss()
        }
        return dialog
    }

    override fun getTheme(): Int {
        return R.style.MyBottomSheet
    }

    interface CategoryBottomSheetDialogListener{
        fun onClickedPopularMovies()
        fun onClickedTopRatedMovies()
        fun onClickedUpComingMovies()
        fun onClickedNowPlayingMovies()
    }
}