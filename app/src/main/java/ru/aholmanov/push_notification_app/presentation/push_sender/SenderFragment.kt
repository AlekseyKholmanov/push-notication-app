package ru.aholmanov.push_notification_app.presentation.push_sender

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.core.view.isVisible
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_push_sender.*
import ru.aholmanov.push_notification_app.App
import ru.aholmanov.push_notification_app.R
import ru.aholmanov.push_notification_app.extension.characterCount
import ru.aholmanov.push_notification_app.model.Priority
import ru.aholmanov.push_notification_app.mvp.AndroidXMvpAppCompatFragment

class SenderFragment : AndroidXMvpAppCompatFragment(), SenderView {

    override fun showSuccess() {
        message_text.text?.clear()
        message_title.text?.clear()
        message_expire.text?.clear()
        message_retry.text?.clear()
        Snackbar.make(sender_layout, R.string.sending_success, Snackbar.LENGTH_SHORT).show()
    }

    override fun showLoading(show: Boolean) {
        progressBar.isVisible = show
        sender_submit_button.isEnabled = !show
    }

    override fun showError(message: String) {
        Snackbar.make(sender_layout, message, Snackbar.LENGTH_LONG).show()
    }

    override fun onInternetStateChanged(connected: Boolean) {
        sender_submit_button.isEnabled = connected
        if (connected) {
            Snackbar.make(sender_layout, R.string.yes_internet, Snackbar.LENGTH_SHORT).show()
        } else {
            Snackbar.make(sender_layout, R.string.no_internet, Snackbar.LENGTH_SHORT).show()
        }
    }

    @InjectPresenter
    lateinit var presenter: SenderPresenter

    @ProvidePresenter
    fun providePresenter() = App.component.getSenderPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_push_sender, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sender_submit_button.setOnClickListener {
            if (isValidToSending()) {
                presenter.sentNotification(
                    user = userKey.text.toString(),
                    message = message_text.text.toString(),
                    title = message_title.text.toString(),
                    priority = Priority.values()[message_spinner.selectedItemPosition],
                    expire = message_expire.text.toString(),
                    retry = message_retry.text.toString()
                )
            }
        }
        message_spinner.adapter =
            ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, Priority.values())
        message_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (position == Priority.EMERGENCY_PRIORITY.ordinal)
                    priority_emergency_fields.visibility = View.VISIBLE
                else {
                    priority_emergency_fields.visibility = View.GONE
                    message_expire.text?.clear()
                    message_retry.text?.clear()
                }
            }
        }
    }

    private fun isValidToSending(): Boolean {
        return when {
            hasEmptyText(userKey) or hasEmptyText(message_text) -> {
                showError(getString(R.string.error_require_field))
                false
            }
            message_text.text?.characterCount() ?: 0 > 1024 -> {
                showError(getString(R.string.error_long_message))
                message_text.error = "message не может быть длиннее 1024 символов"
                false
            }
            message_title.text?.characterCount() ?: 0 > 250 -> {
                Log.d("qwerty", message_title.text?.characterCount().toString())
                showError(getString(R.string.error_long_message))
                message_title.error = "title не может быть больше 250 символов"
                false
            }
            //для Emergency priority retry и expire должны быть заполнены
            Priority.values()[message_spinner.selectedItemPosition] == Priority.EMERGENCY_PRIORITY &&
                    hasEmptyText(message_retry) or hasEmptyText(message_expire) -> {
                val errorMessage = getString(R.string.error_require_field)
                message_retry.error = errorMessage
                message_expire.error = errorMessage
                showError(errorMessage)
                false
            }
            else -> true
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.subscribeToNetworkChanges()
    }

    private fun hasEmptyText(view: EditText): Boolean {
        return when (view.text.isEmpty()) {
            true -> {
                view.error = getString(R.string.error_empty_field)
                true
            }
            false -> false
        }
    }

    companion object {
        fun newInstance(): SenderFragment {
            return SenderFragment()
        }
    }
}