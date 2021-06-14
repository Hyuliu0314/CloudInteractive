package com.hyuliu.cloudinteractive.api

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ApiUtil {
    companion object {
        @JvmStatic
        fun getMethod(url: String):String{
            val TIME_LIMIT = 8000
            val apiURL = URL(url)
            val connection = apiURL.openConnection() as HttpURLConnection
            try {
                connection.requestMethod = "GET"
                connection.connectTimeout = TIME_LIMIT
                connection.readTimeout = TIME_LIMIT
                if (HttpURLConnection.HTTP_OK == connection.responseCode){
                    val inputStream = connection.inputStream
                    val reader = BufferedReader(InputStreamReader(inputStream))
                    val response = StringBuilder()
                    reader.forEachLine {
                        response.append(it)
                    }
                    return response.toString()
                }
            }catch (e: Exception) {
                Log.e("API Error", e.toString())
            }finally {
                connection.disconnect()
            }
            return ""
        }

        @JvmStatic
        fun loadBitmapFromURL(url: String): Bitmap?{
            val option = BitmapFactory.Options()
            option.inPreferredConfig = Bitmap.Config.RGB_565
            val conn = URL(url).openConnection() as HttpURLConnection
            conn.setRequestProperty("content-type","image/png")
            conn.setRequestProperty("User-Agent","Mozilla/5.0 (Linux; Android 11; SM-A205U) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.101 Mobile Safari/537.36")
            if (HttpURLConnection.HTTP_OK == conn.responseCode){
                return BitmapFactory.decodeStream(conn.inputStream)
            }
            return null
        }

    }
}