package iitu.kz.attendancechecker.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class User @JsonCreator constructor(
	@JsonProperty("userId") val userId: Int,
	@JsonProperty("login") val login: String,
	@JsonProperty("password") val password: String,
	@JsonProperty("role") val role: Int
)