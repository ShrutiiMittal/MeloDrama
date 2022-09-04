package com.shrutii.melodrama.utils

import android.content.Context

class AppSharedPref(private val context: Context) {

//    var preferences: SharedPreferences = context.getSharedPreferences()

    private val STRING_TYPE = "String"
    private val BOOLEAN_TYPE = "Boolean"
    private val FLOAT_TYPE = "Float"
    private val LONG_TYPE = "Long"
    private val INTEGER_TYPE = "Integer"

    fun setParam(fileName: String, key: String, value: Any) {
        val type: String = value.javaClass.simpleName

        val sp = context.getSharedPreferences(
            fileName,
            Context.MODE_PRIVATE
        )

        val editor = sp.edit()

        when (type) {
            STRING_TYPE -> editor.putString(
                key,
                value as String?
            )
            INTEGER_TYPE -> editor.putInt(
                key,
                (value as Int?)!!
            )
            BOOLEAN_TYPE -> editor.putBoolean(
                key,
                (value as Boolean?)!!
            )
            FLOAT_TYPE -> editor.putFloat(
                key,
                (value as Float?)!!
            )
            LONG_TYPE -> editor.putLong(
                key,
                (value as Long?)!!
            )
        }

        editor.apply()
    }

    fun getParam(fileName: String, key: String, defaultObject: Any): Any? {

        val type: String = defaultObject.javaClass.simpleName
        val sp =
            context.getSharedPreferences(fileName, Context.MODE_PRIVATE)

        when (type) {
            STRING_TYPE -> return sp.getString(
                key,
                defaultObject.toString()
            )!!
            INTEGER_TYPE -> return sp.getInt(
                key,
                (defaultObject.toString() + "").toInt()
            )
            BOOLEAN_TYPE -> return sp.getBoolean(
                key,
                java.lang.Boolean.parseBoolean(defaultObject.toString() + "")
            )
            FLOAT_TYPE -> return sp.getFloat(
                key,
                (defaultObject.toString() + "").toFloat()
            )
            LONG_TYPE -> return sp.getLong(
                key,
                (defaultObject.toString() + "").toLong()
            )
        }

        return null
    }

    fun clear() {
        val sharedPrefs = context.getSharedPreferences(StringUtils.APP_NAME, Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.clear()
        editor.apply()
    }

}