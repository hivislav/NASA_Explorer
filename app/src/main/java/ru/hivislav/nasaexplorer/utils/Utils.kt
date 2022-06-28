package ru.hivislav.nasaexplorer.utils

import ru.hivislav.nasaexplorer.view.MainFragment
import java.text.SimpleDateFormat
import java.util.*

fun Date.setMyDate(quantityDaysBefore: Int):String {

    val date = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()).split("-")
    val day = date[2].toInt() - quantityDaysBefore

    return "${date[0]}-${date[1]}-$day"

}