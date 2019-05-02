package iitu.kz.attendancechecker.model

data class Attendance(
	val attended: Boolean,
	val lessonId: Int,
	val lessonName: String,
	val teacherName: String,
	val groupName: String
)