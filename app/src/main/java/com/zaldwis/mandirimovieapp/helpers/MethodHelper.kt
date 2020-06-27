package com.zaldwis.mandirimovieapp.helpers

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object MethodHelper{


    fun formatDate(date : String?, format : String) : String{
        var formatedDate = ""

        val sdf =   SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        try {
            val parseDate = sdf.parse(date)
            formatedDate = SimpleDateFormat(format).format(parseDate)
        }catch (e:ParseException){
            e.printStackTrace()
        }

        return formatedDate
    }
}