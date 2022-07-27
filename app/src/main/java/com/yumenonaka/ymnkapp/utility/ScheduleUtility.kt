package com.yumenonaka.ymnkapp.utility
import com.yumenonaka.ymnkapp.models.request.RecentScheduleItem

val scheduleHtmlRegex = Regex("(</?u>|</?html-blob>|</?b>|</?i>)")

fun parseScheduleData(data: ArrayList<RecentScheduleItem>): LinkedHashMap<String, ArrayList<RecentScheduleItem>> {
    var curDate: String = data[0].eventDate // Get the first element date
    val parsedData: LinkedHashMap<String, ArrayList<RecentScheduleItem>> = LinkedHashMap() // Prepare the map to store processed data
    val items: ArrayList<RecentScheduleItem> = ArrayList() // The array list to store list of schedule for particular date (same date)
    items.add(data[0]) // Add first element
    for (i in 1 until data.size) {
        val newDate = data[i].eventDate
        if (curDate != newDate) {
            parsedData[curDate] = ArrayList(items) // if date changed then put all the schedule items into the corresponding date
            curDate = newDate // date changed so update the current date
            items.clear() // clear the items if date changed
        }
        items.add(data[i])
    }
    parsedData[curDate] = ArrayList(items) // Add the last schedule item into the map
    return parsedData
}

fun parseScheduleDescription(desc: String): String {
    return desc.replace(scheduleHtmlRegex, "")
}

fun extractImageFileName(path: String): String? {
    return Regex("[^/]+.(jpg|png)\$").find(path)?.value
}
