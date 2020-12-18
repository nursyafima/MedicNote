package com.example.psiprojek

object Cons {
    val PATH_COLLECTION = "pasien"

    fun setTimeStamp(): Long {
        val time = (-1 * System.currentTimeMillis())
        return time
    }
}