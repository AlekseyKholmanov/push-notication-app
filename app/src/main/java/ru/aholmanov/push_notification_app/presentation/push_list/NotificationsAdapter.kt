package ru.aholmanov.push_notification_app.presentation.push_list

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.aholmanov.push_notification_app.R
import ru.aholmanov.push_notification_app.extension.toStringFormat
import ru.aholmanov.push_notification_app.domain.model.Priority
import ru.aholmanov.push_notification_app.domain.model.SavedNotification

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
        val date: TextView = v.findViewById(R.id.item_sended_date)
        val title: TextView = v.findViewById(R.id.item_title_text)
        val titleContainer: LinearLayout = v.findViewById(R.id.item_title_container)
        val priority: TextView = v.findViewById(R.id.item_priority_text)
        val status: TextView = v.findViewById(R.id.item_status_text)
        val statusImage: ImageView = v.findViewById(R.id.item_status_image)

        fun bind(notification: SavedNotification) {
            user.text = notification.notification.userKey
            message.text = notification.notification.message
            date.text = notification.dateTime.toStringFormat()
            val priorityName = Priority.values()[notification.notification.priority + 2]
            priority.text = priorityName.name

            if (notification.notification.title.isNullOrEmpty()) {
                titleContainer.visibility = View.GONE
            } else
                titleContainer.visibility = View.VISIBLE
                title.text = notification.notification.title

            if (notification.isSuccess) {
                status.text = "success"
                statusImage.setImageResource(R.drawable.ic_ok)
            } else {
                status.text = "error"
                statusImage.setImageResource(R.drawable.ic_fail)
            }
            when (priorityName) {
                Priority.EMERGENCY_PRIORITY -> {
                    v.setBackgroundColor(Color.RED)
                    v.background.alpha = 180
                }
                Priority.HIGHT_PRIORITY -> {
                    v.setBackgroundColor(Color.RED)
                    v.background.alpha = 100
                }
                else -> {
                    v.setBackgroundColor(Color.TRANSPARENT)
                }
            }
        }
    }
}