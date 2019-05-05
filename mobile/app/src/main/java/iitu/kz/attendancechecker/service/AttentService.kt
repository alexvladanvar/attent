package iitu.kz.attendancechecker.service

import iitu.kz.attendancechecker.model.Attendance
import iitu.kz.attendancechecker.model.LoginRequest
import iitu.kz.attendancechecker.model.ResponseData
import iitu.kz.attendancechecker.model.User
import retrofit2.Call
import retrofit2.http.*

interface AttentService {
	@POST("login")
	fun login(@Body loginRequest: LoginRequest): Call<ResponseData>

	@GET("getUserDataV2")
	fun getUserData(@Header("Cookie") cookie: String): Call<User>

	@GET("attendancesV2")
	fun getAttendances(@Header("Cookie") cookie: String): Call<List<Attendance>>

	@GET("/checkAtt/{lessonId}")
	fun checkAttendance(@Header("Cookie") cookie: String, @Path("lessonId") lessonId: Int): Call<ResponseData>
}