package com.hashim.weatherapplication.core.utils.extension

import com.google.gson.Gson
import com.google.gson.GsonBuilder


fun <T> T.toJson(): String = GsonBuilder().disableHtmlEscaping().create().toJson(this)

fun <T> String.toObject(`class`: Class<T>): T = Gson().fromJson(this, `class`)