package com.xplor.android.challenge.network

import okhttp3.Interceptor
import okhttp3.Response

class CustomErrorInterceptor:Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        // Check for your custom error codes here and handle accordingly
        if (!response.isSuccessful) {
            // Handle the error based on the HTTP response code or other conditions
            // You can modify the response or throw a custom exception as needed
            // For example:
            if (response.code == 401) {
                // Handle unauthorized error
            } else if (response.code == 404) {
                // Handle not found error
            }
        }

        return response
    }
}