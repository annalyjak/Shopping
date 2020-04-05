package com.alyjak.shopping

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun Long.formatDateToString(): String {
    val sdf = SimpleDateFormat("dd/MM/yyyy, HH:mm")
    return sdf.format(this)
}