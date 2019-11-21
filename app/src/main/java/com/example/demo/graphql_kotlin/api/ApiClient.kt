package com.example.demo.graphql_kotlin.api

import com.apollographql.apollo.ApolloClient
import okhttp3.OkHttpClient

class ApiClient {
    fun setUpApolloClient(): ApolloClient {
        val okHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
            val original = chain.request()
            val builder = original.newBuilder().method(original.method(), original.body())
            chain.proceed(builder.build())
        }.build()
        return ApolloClient.builder().serverUrl("http://192.168.29.242:4000/graphql").okHttpClient(okHttpClient).build()
    }
}