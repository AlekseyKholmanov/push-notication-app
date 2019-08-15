package ru.aholmanov.push_notification_app.model

enum class Priority(val id: Int) {

    NORMAL_PRIORITY(0),
    LOWEST_PRIORITY(-2),
    LOW_PRIORITY(-1),
    HIGHT_PRIORITY(1),
    EMERGENCY_PRIORITY(2);

    companion object {
        fun getName(id: Int): String {
            var name = ""
            for (i in 0 until values().count()) {
                if (values()[i].id == id)
                    name =  values()[i].name
            }
            return name
        }
    }
}
