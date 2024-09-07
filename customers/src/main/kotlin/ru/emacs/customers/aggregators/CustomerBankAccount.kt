package ru.emacs.customers.aggregators

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import ru.emacs.entities.EntitiesStatus
import java.time.LocalDateTime

@Table("bank_accounts")
data class CustomerBankAccount(
    @field:Id
    val id:Long?,
    val bik: String?,
    val bankName: String,
    val correspondentAccount: String,
    val paymentAccount: String,
    val customerId:Long,
    val status: EntitiesStatus,
    var createdAt: LocalDateTime,
    var updatedAt: LocalDateTime
)
