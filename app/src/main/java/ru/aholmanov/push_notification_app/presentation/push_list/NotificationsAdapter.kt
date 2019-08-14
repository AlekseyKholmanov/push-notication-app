package ru.aholmanov.push_notification_app.presentation.push_list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.aholmanov.push_notification_app.R
import ru.aholmanov.push_notification_app.extension.toFormatTime
import ru.aholmanov.push_notification_app.model.SavedNotification

class NotificationsAdapter(private var notifications: List<SavedNotification> = listOf()) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(R.layout.push_list_item, parent, false)
        return NotificationViewHolder(itemView)
    }

    override fun getItemCount(): Int = notifications.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is NotificationViewHolder)
            holder.bind(notifications[position])
    }
    fun setNews(news: List<SavedNotification>) {
        this.notifications = news
        notifyDataSetChanged()
    }


    class NotificationViewHolder internal constructor(private val v: View) : RecyclerView.ViewHolder(v) {
        val user: TextView = v.findViewById(R.id.item_user_text)
        val message: TextView = v.findViewById(R.id.item_message_text)
        val date:TextView = v. findViewById(R.id.item_sended_date)

        fun bind(notification: SavedNotification) {
            user.text = notification.notification.userKey
            message.text = notification.notification.message
            date.text = notification.dateTime.toFormatTime()
            val color = if (notification.isSuccess)
                Color.GREEN
            else
                Color.RED
            v.setBackgroundColor(color)
            v.background.alpha = 100
        }
    }
}