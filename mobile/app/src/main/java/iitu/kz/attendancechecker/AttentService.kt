package iitu.kz.attendancechecker

import iitu.kz.attendancechecker.entities.LoginRequest
import iitu.kz.attendancechecker.entities.ResponseData
import iitu.kz.attendancechecker.entities.User
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