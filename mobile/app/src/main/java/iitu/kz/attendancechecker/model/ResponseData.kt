package iitu.kz.attendancechecker.model

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

class ResponseData @JsonCreator constructor(
    @JsonProperty("success") var success: Boolean
)