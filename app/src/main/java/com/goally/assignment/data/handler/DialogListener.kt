package com.retech.yapiee.data.handler

import android.view.View
import android.widget.DatePicker
import android.widget.RadioGroup
import androidx.appcompat.app.AlertDialog

interface DialogListener {
    fun onPositiveClicked()
    fun onNegativeClicked()
}

interface OnClickListener {
    fun onClickListener(stateId: Int,checked:String, alertDialog: AlertDialog)
}

interface OnDatePickerListener{
    fun onDatePickerListener(view: DatePicker, day: Int, month: Int,year: Int )
}