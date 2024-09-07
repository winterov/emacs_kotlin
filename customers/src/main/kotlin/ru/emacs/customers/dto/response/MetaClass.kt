package ru.emacs.customers.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class MetaClass (
    val status:String,
    @field:JsonProperty("today_request_count")
    val todayRequestCount :Int,
    val message:String?,
    val balance: Double
)