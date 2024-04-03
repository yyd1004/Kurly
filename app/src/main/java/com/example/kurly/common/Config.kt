package com.example.kurly.common

import android.content.Context

class Config {
    companion object {
        const val HORIZONTAL_TYPE = 0
        const val GRID_TYPE = 1
        const val VERTICAL_TYPE = 2

        fun saveLikeArrayList(context: Context, list: ArrayList<String>, key: String) {
            val sharedPreferences = context.getSharedPreferences("like_list", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putInt("${key}_size", list.size)
            for (i in list.indices) {
                editor.putString("${key}_$i", list[i])
            }
            editor.apply()
        }

        fun loadLikeArrayList(context: Context, key: String): ArrayList<String>? {
            val sharedPreferences = context.getSharedPreferences("like_list", Context.MODE_PRIVATE)
            val size = sharedPreferences.getInt("${key}_size", 0)
            val list = ArrayList<String>()
            for (i in 0 until size) {
                val value = sharedPreferences.getString("${key}_$i", null)
                value?.let { list.add(it) }
            }
            return list
        }
    }
}