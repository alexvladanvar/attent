package iitu.kz.attendancechecker.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class Attendance @JsonCreator constructor(
	@JsonProperty("attended") val attended: Boolean,
	@JsonProperty("lessonId") val lessonId: Int,
	@JsonProperty("lessonName") val lessonName: String,
	@JsonProperty("teacherName") val teacherName: String,
	@JsonProperty("groupName") val groupName: String
)