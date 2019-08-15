package ru.aholmanov.push_notification_app.presentation.push_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.fragment_push_sended_list.*
import ru.aholmanov.push_notification_app.App
import ru.aholmanov.push_notification_app.R
import ru.aholmanov.push_notification_app.model.SavedNotification
import ru.aholmanov.push_notification_app.mvp.AndroidXMvpAppCompatFragment

class SendedListFragment : AndroidXMvpAppCompatFragment(), SendedListView {
    private lateinit var adapter: NotificationsAdapter

    override fun showError(t: Throwable) {
        if (adapter.itemCount == 0) {
            showEmptyPlaceholder()
        }
    }

    override fun showNotifications(notifications: List<SavedNotification>) {
        if (notifications.isEmpty()) {
            showEmptyPlaceholder()
        } else {
            emptyPlaceholder.isVisible = false
            notificationsList.isVisible = true
            adapter.setNews(notifications)
        }
    }

    private fun showEmptyPlaceholder() {
        emptyPlaceholder.isVisible = true
        notificationsList.isVisible = false

        val textRes = R.string.error_empty_list
        emptyPlaceholderText.setText(textRes)
    }

    @InjectPresenter
    lateinit var presenter: SendedListPresenter

    @ProvidePresenter
    fun providePresenter() = App.component.getSendedListPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_push_sended_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = NotificationsAdapter()
        val lm = LinearLayoutManager(requireContext())
        notificationsList.layoutManager = lm
        notificationsList.adapter = adapter
        val decoration = DividerItemDecoration(context, lm.orientation)
        decoration.setDrawable(context!!.getDrawable(R.drawable.divider))
        notificationsList.addItemDecoration(decoration)
        presenter.observeNotification()
    }

    companion object {
        fun newInstance(): SendedListFragment {
            return SendedListFragment()
        }
    }
}
