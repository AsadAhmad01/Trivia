package com.goally.assignment.data.utils

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.goally.assignment.R
import com.goally.assignment.databinding.CustomAlertLayoutBinding
import com.goally.assignment.presentations.ui.activities.HomeActivity
import com.retech.yapiee.data.handler.DialogListener
import com.retech.yapiee.data.handler.OnDatePickerListener
import java.util.*


object CustomDialogs {


    fun datePickerDialog(context: Context, pickerListener: OnDatePickerListener) {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(context, { view, year, monthOfYear, dayOfMonth ->

            pickerListener.onDatePickerListener(view, dayOfMonth, monthOfYear, year)

        }, year, month, day)

        dpd.show()
    }

    fun singleDialogWithoutListener(
        context: Context, message: String,
        positiveText: String, title: String = context.getString(
            R.string.app_name
        )
    ) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setCancelable(false)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton(positiveText) { dialog, _ ->
            dialog!!.dismiss()
        }.show()
    }

    fun singleDialogWithListener(
        context: Context, message: String, positiveText: String,
        listener: DialogListener, title: String = context.getString(
            R.string.app_name
        )
    ) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setCancelable(false)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton(positiveText) { dialog, _ ->
            dialog!!.dismiss()
            listener.onPositiveClicked()
        }.show()
    }

    fun choiceDialogWithListener(
        context: Context, message: String,
        positiveText: String, negativeText: String,
        listener: DialogListener, title: String = context.getString(R.string.app_name)
    ) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setCancelable(false)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton(positiveText) { dialog, _ ->
            dialog!!.dismiss()
            listener.onPositiveClicked()
        }.setNegativeButton(negativeText) { dialog, _ ->
            dialog.dismiss()
            listener.onNegativeClicked()
        }.show()
    }

    fun showInternetMessage(context: Context) {
        singleDialogWithoutListener(
            context,
            context.resources.getString(R.string.internet_connection),
            context.resources.getString(R.string.ok),
            context.resources.getString(R.string.app_name)
        )
    }

    fun showDialog(activity: Activity, id: Int, message: String, btn: String, intentCheck: String) {

        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val binding: CustomAlertLayoutBinding = DataBindingUtil.inflate(
            LayoutInflater.from(activity),
            R.layout.custom_alert_layout,
            null,
            false
        )
        builder.setView(binding.root)

        val dialog: AlertDialog = builder.create()

        if (id == 1) {
            binding.btnNoCustom.visibility = View.VISIBLE
        } else {
            binding.btnNoCustom.visibility = View.INVISIBLE
        }

        builder.setCancelable(false)
        binding.btnYesCustom.text = btn
        binding.txtDialogMessage.text = message
        binding.txtDialogMessage2.visibility = View.GONE
        binding.btnYesCustom.setOnClickListener {
            if (intentCheck == "GAME") {
                dialog.dismiss()
            } else {

                val intent = Intent(activity, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                activity.startActivity(intent)
                activity.finish()
                activity.overridePendingTransition(
                    0, R.anim.slide_out_right
                )

                dialog.dismiss()
            }
        }
        binding.btnNoCustom.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()

    }


}