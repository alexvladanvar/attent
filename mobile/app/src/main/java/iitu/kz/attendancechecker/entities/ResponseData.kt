package iitu.kz.attendancechecker.entities

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class ResponseData @JsonCreator constructor(
    @JsonProperty("success") var success: Boolean
)