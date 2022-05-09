package com.avocadochif.wear.weather.networking.error

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import retrofit2.Response

object HttpErrorChecker {

    inline fun <T, reified E : ErrorResponse> checkError(response: Response<T>): ApiError<ErrorResponse> {
        try {
            val errorJson = response.errorBody()?.string().orEmpty()
            val errorJSONObject = JSONObject(errorJson)

            //Getting a root error message
            val errorMessage = getRootErrorMessage(errorJSONObject)

            //Getting a custom error model if exist
            val customError = fetchCustomError<E>(errorJson)

            //Return full error data
            return ApiError(errorMessage, ErrorType.UNSPECIFIED, customError)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("NETWORKING: ", "checkError() failed with Exception. Error message " + e.localizedMessage)
            return getErrorBasedOnResponseCode(response.code())
        }
    }

    fun getRootErrorMessage(jsonObject: JSONObject): String = try {
        jsonObject.getString("message")
    } catch (e: Exception) {
        e.printStackTrace()
        Log.e("NETWORKING: ", "getting root error message failed with Exception. Error message " + e.localizedMessage)
        "Unknown root error"
    }

    inline fun <reified E : ErrorResponse> fetchCustomError(errorBodyJson: String): E? {
        return try {
            Gson().fromJson<E>(errorBodyJson, object : TypeToken<E>() {}.type)
        } catch (e: JsonSyntaxException) {
            Log.e("NETWORKING: ","fetchCustomError() failed with JsonSyntaxException. Error message " + e.localizedMessage)
            e.printStackTrace()
            null
        }
    }

    inline fun <reified E : ErrorResponse> getErrorBasedOnResponseCode(code: Int): ApiError<E> {
        return when (code) {
            401 -> ApiError("User is not authorized to do this action", ErrorType.AUTHORIZATION)
            403 -> ApiError("The requested operation is forbidden and cannot be completed", ErrorType.AUTHORIZATION)
            404 -> ApiError("The requested operation failed because a resource associated with the request could not be found", ErrorType.NOT_FOUND)
            422 -> ApiError("Sent data isn't valid", ErrorType.VALIDATION_ERROR)
            429 -> ApiError("Too many requests have been sent within a given time span", ErrorType.TOO_MANY_REQUESTS)
            500 -> ApiError("The request failed due to an internal server error", ErrorType.SERVER_ERROR)
            else -> ApiError("Something else went wrong", ErrorType.UNSPECIFIED)
        }
    }

}