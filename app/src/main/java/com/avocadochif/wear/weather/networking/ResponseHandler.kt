package com.avocadochif.wear.weather.networking

import android.util.Log
import com.avocadochif.wear.weather.networking.error.ApiError
import com.avocadochif.wear.weather.networking.error.ErrorResponse
import com.avocadochif.wear.weather.networking.error.ErrorType
import com.avocadochif.wear.weather.networking.error.HttpErrorChecker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T : Any?> safeApiCall(call: suspend () -> Result<T>): Result<T> = try {
    withContext(Dispatchers.IO) { call.invoke() }
} catch (e: HttpException) {
    Result.Error(ApiError(e.message(), ErrorType.UNSPECIFIED))
} catch (e: UnknownHostException) {
    Result.Error(ApiError("No internet connection", ErrorType.NO_NETWORK))
} catch (e: SocketTimeoutException) {
    Result.Error(ApiError("Bad internet connection", ErrorType.NETWORK_TIMEOUT))
} catch (e: Throwable) {
    Log.e("Internal API ERROR", e.stackTrace.toString())
    Result.Error(ApiError("Oops something went wrong.", ErrorType.INTERNAL_ERROR))
}

fun <T : Any> Response<T>.baseResponseHandler(): Result<T> {
    return when (this.isSuccessful) {
        true -> Result.Success(this.body())
        false -> Result.Error(HttpErrorChecker.checkError<T, ErrorResponse>(this))
    }
}

inline fun <T : Any?, reified E : ErrorResponse> Response<T>.baseResponseHandlerWithCustomError(): Result<T?> {
    return when (this.isSuccessful) {
        true -> Result.Success(this.body())
        false -> Result.Error(HttpErrorChecker.checkError<T, E>(this))
    }
}
