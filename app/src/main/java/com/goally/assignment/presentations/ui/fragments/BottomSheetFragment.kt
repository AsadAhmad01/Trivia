package com.goally.assignment.presentations.ui.fragments

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import com.goally.assignment.data.base.App
import com.goally.assignment.data.preferences.CustomSharedPref
import com.goally.assignment.databinding.FragmentBottomSheetBinding
import com.goally.assignment.presentations.ui.activities.LoginActivity
import com.goally.assignment.presentations.ui.activities.UpdateProfileActivity
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetBinding
    private lateinit var sharedPref: CustomSharedPref

    companion object {
        fun newInstance(): BottomSheetFragment {
            return BottomSheetFragment()
        }
    }

    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {

        binding = FragmentBottomSheetBinding.inflate(layoutInflater)
        sharedPref = CustomSharedPref(App.context)
        dialog.setContentView(binding.root)
        dialog.setCancelable(false)

        setListeners()
    }

    private fun setListeners() {
        binding.imgBack.setOnClickListener {
            dialog?.dismiss()
        }

        binding.txtEditProfile.setOnClickListener {
            startActivity(Intent(App.context, UpdateProfileActivity::class.java))
            dialog?.dismiss()
        }

        binding.txtLogout.setOnClickListener {
            sharedPref.clearSharedPreference()
            val i = Intent(App.context, LoginActivity::class.java)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(i)
            dialog?.dismiss()
        }
    }

}