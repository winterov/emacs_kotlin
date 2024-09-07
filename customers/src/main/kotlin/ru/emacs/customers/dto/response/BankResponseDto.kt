package ru.emacs.customers.dto.response

import com.fasterxml.jackson.annotation.JsonSetter


data class BankResponseDto(
    val data: Bank?, val meta: MetaClass
)

data class Bank(
    @field:JsonSetter("БИК")
    val bik: String,
    @field:JsonSetter("Наим")
    val title: String,
    @field:JsonSetter("КорСчет")
    val correspondentAccount: CorrespondentAccount
    )

data class CorrespondentAccount(
    @field:JsonSetter("Номер")
    val account: String
)
