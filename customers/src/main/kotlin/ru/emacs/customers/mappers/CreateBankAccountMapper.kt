package ru.emacs.customers.mappers



import ru.emacs.customers.dto.request.RequestCreateBankAccount
import ru.emacs.customers.aggregators.CustomerBankAccount
import ru.emacs.entities.EntitiesStatus
import java.time.LocalDateTime

class CreateBankAccountMapper {
    fun fromDto(
        dto: RequestCreateBankAccount, createdAt: LocalDateTime, updatedAt: LocalDateTime
    ): CustomerBankAccount {
        return CustomerBankAccount(
            null,
            dto.bik,
            dto.title,
            dto.correspondentAccount!!,
            dto.paymentAccount!!,
            dto.customerId!!,
            EntitiesStatus.ACTIVE,
            createdAt,
            updatedAt
        )

    }
}