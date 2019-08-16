package ru.aholmanov.push_notification_app.presentation.push_sender

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import org.joda.time.DateTime

class MyDatePickerDialog : DialogFragment(), DatePickerDialog.OnDateSetListener {

    companion object {
        fun newInstance(): MyDatePickerDialog {
            return MyDatePickerDialog()
        }
    }
    private var callback: IDatePickerCallback? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val time = DateTime()
        val startDate: Long = DateTime().millis
        val dialog = DatePickerDialog(context!!, this, time.year, time.monthOfYear - 1, time.dayOfMonth)
        dialog.datePicker.minDate = startDate
        return dialog
    }

    override fun onDateSet(picker: DatePicker, year: Int, month: Int, dayOfMonth: Int) {
        val date = DateTime(picker.year, picker.month + 1, picker.dayOfMonth, 0, 0)
        val timePicker = MyTimePickerDialog.newInstance(date,callback)
        timePicker.show(fragmentManager, "timePicker")
    }

    fun setListener(callback: IDatePickerCallback) {
        this.callback = callback
    }
}

interface IDatePickerCallback {
    fun datePicked(date: DateTime)
}
