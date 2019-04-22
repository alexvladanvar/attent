package iitu.kz.attendancechecker

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory


object RetrofitClient {
    val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.57:8080/")
        .addConverterFactory(JacksonConverterFactory.create())
        .build()
}