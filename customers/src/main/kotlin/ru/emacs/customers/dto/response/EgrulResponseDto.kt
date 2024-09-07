package ru.emacs.customers.dto.response


import com.fasterxml.jackson.annotation.JsonSetter
import java.util.*
import kotlin.collections.List


class EgrulResponseDto(val data: Egrul, val meta: MetaClass){

    data class Egrul(
        var isBusy:Boolean=false,
        @field:JsonSetter("ИНН")
        var inn:String,
        @field:JsonSetter("КПП")
        var kpp:String,
        @field:JsonSetter("ОГРН")
        var ogrn:String,
        @field:JsonSetter("ОКПО")
        var okpo:String,
        @field:JsonSetter("ДатаРег")
        var createdDate: Date,
        @field:JsonSetter("НаимПолн")
        var fullName:String,
        @field:JsonSetter("НаимСокр")
        var abbreviatedName:String,
        @field:JsonSetter("ЮрАдрес")
        var address: AddressUL,
        @field:JsonSetter("Статус")
        var status: StatusUL,
        @field:JsonSetter("Руковод")
        var administration:List<Administration>,
        @field:JsonSetter("Налоги")
        var taxes: Taxes?,
        @field:JsonSetter("СЧР")
        var averageEmployees:Int?,
        @field:JsonSetter("СЧРГод")
        var averageEmployeesYear:Int?,
        @field:JsonSetter("НедобПост")
        var unscrupulous:Boolean,
        @field:JsonSetter("ДисквЛицо")
        var disqualification:Boolean,
        @field:JsonSetter("МассРуковод")
        var massManagement:Boolean,
        @field:JsonSetter("МассУчред")
        var massOwner:Boolean,
        @field:JsonSetter("НелегалФин")
        var illegallyFinance:Boolean,
        @field:JsonSetter("Санкции")
        var sanction:Boolean,
    )
    data class AddressUL(
        @field:JsonSetter("АдресРФ")
        var address:String,
        @field:JsonSetter("Недост")
        var isWrong:Boolean
    )
    data class StatusUL(
        @field:JsonSetter("Код")
        var kod:String,
        @field:JsonSetter("Наим")
        var status:String
    )
    data class Administration(
        @field:JsonSetter("ФИО")
        var fio:String,
        @field:JsonSetter("НаимДолжн")
        var position:String,
        @field:JsonSetter("Недост")
        var isWrong:Boolean,
        @field:JsonSetter("ДисквЛицо")
        var disqualification:Boolean,
        @field:JsonSetter("МассРуковод")
        var massManagement:Boolean
    )
    data class Taxes(
        @field:JsonSetter("ОсобРежим")
        var taxesMode:List<String>?,
        @field:JsonSetter("СумНедоим")
        var arrears:String?,
        @field:JsonSetter("НедоимДата")
        var arrearsDate:Date?
    )

}

