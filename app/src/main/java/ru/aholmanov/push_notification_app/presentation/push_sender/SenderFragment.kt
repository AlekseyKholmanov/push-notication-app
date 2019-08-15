package ru.aholmanov.push_notification_app.presentation.push_sender

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.isVisible
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_push_sender.*
import ru.aholmanov.push_notification_app.App
import ru.aholmanov.push_notification_app.R
import ru.aholmanov.push_notification_app.mvp.AndroidXMvpAppCompatFragment

class SenderFragment : AndroidXMvpAppCompatFragment(), SenderView {
    override fun showSuccess() {
        message_text.text?.clear()
        Snackbar.make(sender_layout, R.string.sending_success, Snackbar.LENGTH_SHORT).show()
    }

    override fun showLoading(show: Boolean) {
        progressBar.isVisible = show
        sender_submit_button.isEnabled = !show
    }

    override fun showError(message: String) {
        Snackbar.make(sender_layout, message, Snackbar.LENGTH_SHORT).show()
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
                    message = message_text.text.toString()
                )
            }
        }
    }

    private fun isValidToSending(): Boolean {
        return when {
            hasEmptyText(userKey) or  hasEmptyText(message_text) -> {
                showError(getString(R.string.sending_error))
                false
            }
            message_text.text.toString().toByteArray(Charsets.UTF_8).count() > 1024 -> {
                showError(getString(R.string.error_long_message))
                message_text.error = "слишком длинное сообщение"
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
                view.error = "поле не может быть пустым"
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