package ru.aholmanov.push_notification_app.model

enum class Priority(val id: Int) {
    LOWEST_PRIORITY(-2),
    LOW_PRIORITY(-1),
    NORMAL_PRIORITY(0),
    HIGHT_PRIORITY(1),
    EMERGENCY_PRIORITY(2)
}
