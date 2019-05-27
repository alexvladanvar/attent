package iitu.kz.attendancechecker.service

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

object NetworkService {
	val retrofit: Retrofit = Retrofit.Builder()
		.baseUrl("http://46.161.39.30:8080/")
		.addConverterFactory(JacksonConverterFactory.create())
		.build()
}