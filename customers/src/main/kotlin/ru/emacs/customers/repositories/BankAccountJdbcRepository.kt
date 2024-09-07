package ru.emacs.customers.repositories


import org.springframework.data.repository.CrudRepository
import ru.emacs.customers.aggregators.CustomerBankAccount


interface BankAccountJdbcRepository: CrudRepository<CustomerBankAccount, Long> {
    fun findByBikAndPaymentAccount(bic: String, paymentAccount: String): CustomerBankAccount?
}