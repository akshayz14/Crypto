package com.akshay.upstoxassignment.base

class Utils {
    fun String?.toStringOrEmpty() : String {
        return this ?: ""
    }

}