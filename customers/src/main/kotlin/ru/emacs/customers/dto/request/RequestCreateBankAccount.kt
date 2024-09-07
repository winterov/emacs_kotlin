package ru.emacs.customers.dto.request

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import ru.emacs.validators.BankAccount
import ru.emacs.validators.RequestBankAccount

@BankAccount
data class RequestCreateBankAccount(
    val bik: String?,
    @field:NotBlank
    @field:Size(max = 200)
    val title: String,
    val correspondentAccount: String?,
    val paymentAccount: String?,
    @field:NotNull
    val customerId:Long?,
): RequestBankAccount {
    override fun bic(): String? {
        return bik
    }

    override fun correspondentAccount(): String? {
        return correspondentAccount
    }

    override fun paymentAccount(): String? {
        return paymentAccount
    }
}