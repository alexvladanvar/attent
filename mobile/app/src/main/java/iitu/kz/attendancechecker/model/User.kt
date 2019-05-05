package iitu.kz.attendancechecker.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class User @JsonCreator constructor(
	@JsonProperty("login") val login: String,
	@JsonProperty("role") val role: Int,
	@JsonProperty("firstName") val firstName: String,
	@JsonProperty("lastName") val lastName: String,
	@JsonProperty("groupName") val groupName: String
)