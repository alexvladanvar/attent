package iitu.kz.attendancechecker.service

import iitu.kz.attendancechecker.model.LoginRequest
import iitu.kz.attendancechecker.model.ResponseData
import iitu.kz.attendancechecker.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AttentService {
	@POST("login")
	fun login(@Body loginRequest: LoginRequest): Call<ResponseData>

	@GET("me")
	fun userInfo(@Header("Cookie") cookie: String): Call<User>
}