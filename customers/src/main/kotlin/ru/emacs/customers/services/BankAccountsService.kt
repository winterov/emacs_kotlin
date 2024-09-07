package ru.emacs.customers.services

import org.springframework.stereotype.Service
import ru.emacs.customers.dto.request.RequestCreateBankAccount
import ru.emacs.customers.dto.response.BankResponseDto
import ru.emacs.customers.aggregators.CustomerBankAccount


@Service
interface BankAccountsService {
    fun loadBank(bik:String?): BankResponseDto?
    fun createBankAccount(bank: RequestCreateBankAccount): CustomerBankAccount
}