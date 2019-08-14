package ru.aholmanov.push_notification_app.presentation.push_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import ru.aholmanov.push_notification_app.App
import ru.aholmanov.push_notification_app.R
import ru.aholmanov.push_notification_app.mvp.AndroidXMvpAppCompatFragment

class SendedListFragment:AndroidXMvpAppCompatFragment(),SendedListView {

    @InjectPresenter
    lateinit var presenter: SendedListPresenter

    @ProvidePresenter
    fun providePresenter() = App.component.getSendedListPresenter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_push_sended_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun newInstance(): SendedListFragment {
            return SendedListFragment()
        }
    }
}
