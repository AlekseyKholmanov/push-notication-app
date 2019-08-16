package ru.aholmanov.push_notification_app.presentation.push_sender

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.TimePicker
import androidx.fragment.app.DialogFragment
import org.joda.time.DateTime

class MyTimePickerDialog : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    companion object {
        fun newInstance(date: DateTime, callback: IDatePickerCallback?): MyTimePickerDialog {
            val instance = MyTimePickerDialog()
            val args = Bundle()
            args.putLong("startDate", date.millis)
            instance.arguments = args
            instance.setListener(callback!!)
            return instance
        }
    }

    private var callback: IDatePickerCallback? = null
    lateinit var date: DateTime

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val time = DateTime()
        date = DateTime(arguments?.getLong("startDate"))
        return TimePickerDialog(context, this, time.hourOfDay, time.minuteOfDay, true)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

        val finalDate = date.plusHours(hourOfDay).plusMinutes(minute)
        callback?.datePicked(finalDate)
    }

    fun setListener(callback: IDatePickerCallback) {
        this.callback = callback
    }
}