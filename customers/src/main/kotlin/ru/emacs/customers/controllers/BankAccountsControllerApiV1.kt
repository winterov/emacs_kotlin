package ru.emacs.customers.controllers

import jakarta.validation.Validator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import ru.emacs.customers.dto.request.RequestCreateBankAccount
import ru.emacs.customers.services.BankAccountsService
import ru.emacs.dtos.AppResponseErrorDto


@RestController
@RequestMapping("/api/v1/sales/bank_accounts")
class BankAccountsControllerApiV1 @Autowired constructor(
    private val bankAccountsService: BankAccountsService,
    private val validator: Validator
    ) {

    @GetMapping("bank")
    fun loadBank(@RequestParam bik:String?):ResponseEntity<Any>{
        val bank = bankAccountsService.loadBank(bik)
        if(bank!=null) return ResponseEntity.ok(bank)
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @PutMapping("create")
    fun createBankAccount(@RequestBody dto: RequestCreateBankAccount):ResponseEntity<Any>{
        val violations = validator.validate(dto)
        val errorMessage: MutableList<String> = ArrayList(3)
        if (violations.isNotEmpty()) {
            for (violation in violations) {
                errorMessage.add(violation.message)
            }
        }
        if(errorMessage.isNotEmpty()) {
            val errorDto = AppResponseErrorDto(HttpStatus.BAD_REQUEST, errorMessage)
            return ResponseEntity(errorDto, HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity.ok(bankAccountsService.createBankAccount(dto))
    }
}