package ru.aholmanov.push_notification_app.presentation.push_sender

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_push_sender.*
import ru.aholmanov.push_notification_app.App
import ru.aholmanov.push_notification_app.R
import ru.aholmanov.push_notification_app.model.PushNotification
import ru.aholmanov.push_notification_app.mvp.AndroidXMvpAppCompatFragment

class SenderFragment : AndroidXMvpAppCompatFragment(), SenderView {
    override fun showSuccess() {
        Toast.makeText(context, "Уведомление успешно отправлено", Toast.LENGTH_SHORT).show()
    }

    override fun showLoading(show: Boolean) {
        progressBar.isVisible = show
        sender_submit_button.isEnabled = !show
    }

    override fun onError(error: Throwable) {
        Toast.makeText(context, "ошибка при отправке", Toast.LENGTH_SHORT).show()
    }

    override fun onInternetStateChanged(connected: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
            if (!hasEmptyText(appToken) and !hasEmptyText(userKey) and !hasEmptyText(message_text)) {
                val notification = PushNotification(
                    token = appToken.text.toString(),
                    userKey = userKey.text.toString(),
                    message = message_text.text.toString()
                )
                presenter.sentNotification(notification)
            } else
                Toast.makeText(context, "не заполнены обязательные поля", Toast.LENGTH_SHORT).show()
        }
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