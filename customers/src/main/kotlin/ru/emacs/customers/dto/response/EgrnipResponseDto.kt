package ru.emacs.customers.dto.response

import com.fasterxml.jackson.annotation.JsonSetter
import java.util.*


class EgrnipResponseDto(val data: Egrnip, val meta: MetaClass){

    data class Egrnip(
        var isBusy:Boolean=false,
        @field:JsonSetter("ОГРНИП")
        var ogrnip:String,
        @field:JsonSetter("ИНН")
        var inn:String,
        @field:JsonSetter("ОКПО")
        var okpo:String,
        @field:JsonSetter("ДатаРег")
        var createdDate: Date,
        @field:JsonSetter("Статус")
        var status: StatusIP,
        @field:JsonSetter("ФИО")
        var fullName:String,
        @field:JsonSetter("НедобПост")
        var unscrupulous:Boolean,
        @field:JsonSetter("МассРуковод")
        var massManagement:Boolean,
        @field:JsonSetter("МассУчред")
        var massOwner:Boolean,

        )
    data class StatusIP(
        @field:JsonSetter("Код")
        var kod:String,
        @field:JsonSetter("Наим")
        var status:String
    )
}

