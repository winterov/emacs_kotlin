package ru.emacs.sales.services.impl

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import ru.emacs.customers.dto.request.RequestCreateBankAccount
import ru.emacs.customers.dto.response.BankResponseDto
import ru.emacs.customers.mappers.CreateBankAccountMapper
import ru.emacs.customers.aggregators.CustomerBankAccount
import ru.emacs.customers.repositories.BankAccountJdbcRepository
import ru.emacs.customers.services.BankAccountsService
import ru.emacs.validators.BankAccountValidator

import java.time.LocalDateTime


@Service
class BankAccountsServiceImpl @Autowired constructor(
    @Value("\${ofdata.key}") private val key:String,
    private val bankAccountJdbcRepository: BankAccountJdbcRepository
    ): BankAccountsService {
    private val logger = LoggerFactory.getLogger(BankAccountsServiceImpl::class.java)
    companion object{
        private const val BANK_URL = "https://api.ofdata.ru/v2/bank?key="
    }
    override fun loadBank(bik: String?): BankResponseDto? {
        val validator = BankAccountValidator()
        if(!validator.validateBIC(bik)){
            return null
        }
        val client = RestClient.create()
        val url = "$BANK_URL$key&bic=$bik"
        var result: BankResponseDto? = null
        try {
            result=client.get()
                .uri(url)
                .retrieve()
                .body(BankResponseDto::class.java)
        }catch (e:Exception){
            this.logger.error(e.message+" bic $bik")
        }
        return result
    }

    override fun createBankAccount(bank: RequestCreateBankAccount): CustomerBankAccount {
       return  bankAccountJdbcRepository.save(CreateBankAccountMapper().fromDto(bank, LocalDateTime.now(), LocalDateTime.now()))
    }
}