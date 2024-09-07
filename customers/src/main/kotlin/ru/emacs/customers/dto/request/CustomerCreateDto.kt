package ru.emacs.customers.dto.request

import com.fasterxml.jackson.annotation.JsonSetter

import ru.emacs.customers.aggregators.CustomerType
import ru.emacs.customers.validators.CustomerDto


@CustomerDto
data class CustomerCreateDto(
    val type: CustomerType?,
    val parentId:Long,
    val fullName: String?,
    val abbreviatedName: String?,
    val inn: String?,
    val kpp: String?,
    val ogrn: String?,
    val okpo: String?,
    var address: String?,
    @field:JsonSetter("chief_position")
    val chiefPosition:String?,
    @field:JsonSetter("chief_fio")
    val chiefFio: String?,
    val passport:String?,
    )
