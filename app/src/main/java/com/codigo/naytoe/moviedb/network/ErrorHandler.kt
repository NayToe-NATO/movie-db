package com.codigo.naytoe.moviedb.network

import org.json.JSONObject
import retrofit2.Response

object ErrorHandler {

    fun apiError(response: Response<*>) : String {
        val jsonObject: JSONObject
        var message: String
        try {
            val errorBody = response.errorBody()!!.string()
            jsonObject = JSONObject(errorBody)
            message = jsonObject.getString("detail")
        } catch (e: Exception) {
            e.printStackTrace()
            message = "Something went wrong"
        }
        return message
    }

    fun networkError(message: String?) : String? {
        return message
    }

}