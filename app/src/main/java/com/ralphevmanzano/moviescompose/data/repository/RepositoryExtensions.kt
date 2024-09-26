package com.ralphevmanzano.moviescompose.data.repository

import com.skydoves.sandwich.ApiResponse
import com.skydoves.sandwich.getOrNull
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess

fun <T> handleApiResponse(
    response: ApiResponse<T>
): T? {
    return response.onSuccess {
        data
    }.onError {
        // Handle error, log, or perform an action if needed
        throw Exception(message())
    }.onException {
        // Handle exception, such as network failure
        throw Exception(throwable)
    }.getOrNull() // Return null in case of failure
}