package ru.emacs.customers.services.impl

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestClient
import ru.emacs.customers.dto.request.CustomerCreateDto
import ru.emacs.customers.dto.response.EgrnipResponseDto
import ru.emacs.customers.dto.response.EgrulResponseDto
import ru.emacs.customers.mappers.CreateCustomerMapper
import ru.emacs.customers.aggregators.Customer
import ru.emacs.customers.aggregators.CustomerType
import ru.emacs.customers.repositories.CustomerJdbcRepository
import ru.emacs.customers.repositories.CustomerRepository
import ru.emacs.entities.EntitiesStatus

import ru.emacs.customers.services.CustomersService
import ru.emacs.exceptions.AppError
import ru.emacs.validators.INNValidator
import ru.emacs.validators.OGRNValidator
import ru.emacs.validators.OGRNValidatorType
import java.time.LocalDateTime

@Service
class CustomerServiceImpl(
    private val customerRepository: CustomerRepository,
    private val customerJdbcRepository: CustomerJdbcRepository,
    @Value("\${ofdata.key}") private val key:String
    ) : CustomersService {
    companion object {
        private const val EGRUL_URL = "https://api.ofdata.ru/v2/company"
        private const val ERNIP_URL = "https://api.ofdata.ru/v2/entrepreneur"

    }

    private val logger = LoggerFactory.getLogger(CustomerServiceImpl::class.java)

    private fun checkINNisBusy(inn: String): Boolean {
        val count = customerJdbcRepository.countByInnAndStatus(inn, EntitiesStatus.ACTIVE)
        return count != 0
    }

    private fun checkORGNisBusy(ogrn: String): Boolean {
        val count = customerJdbcRepository.countByOgrnAndStatus(ogrn, EntitiesStatus.ACTIVE)
        return count != 0
    }
    @Transactional
    override fun createCustomer(dto: CustomerCreateDto): Customer? {
        if(dto.type==CustomerType.BUSINESS||dto.type==CustomerType.ENTREPRENEUR) {
            if(checkORGNisBusy(dto.ogrn!!)){
                throw AppError("Контрагент с таким ОГРН уже существует")
            }
        }
        val parentCustomer=customerRepository.findById(dto.parentId)
        if(parentCustomer==null||parentCustomer.type!=CustomerType.GROUP){
            throw AppError("Элемент с id:${dto.parentId} не является группой")
        }
        val created = LocalDateTime.now()
        return customerRepository.save(CreateCustomerMapper.fromDto(dto,created,created))
    }


    override fun loadCustomerBusiness(ogrn: String?, inn: String?): EgrulResponseDto? {
        if (ogrn.isNullOrBlank() && inn.isNullOrEmpty()) return null
        val isBusy = if (!ogrn.isNullOrBlank()) {
            checkORGNisBusy(ogrn)
        } else {
            checkINNisBusy(inn!!)
        }
        val ogrnValidator = OGRNValidator()
        val innValidator = INNValidator()
        if (!ogrnValidator.isValid(ogrn, null) && !innValidator.isValid(inn, null)) return null
        var param = ""
        if (ogrn != null) {
            param = "&ogrn=$ogrn"
        }
        if (inn != null) {
            param = "$param&inn=$inn"
        }
        val client = RestClient.create()
        var result: EgrulResponseDto? = null
        try {
            result = client.get().uri("$EGRUL_URL?key=$key$param")
                .retrieve()
                .body(EgrulResponseDto::class.java)
            result?.data?.isBusy = isBusy
        } catch (ex: Exception) {
            logger.error(ex.stackTraceToString())
        }
        return result
    }

    override fun loadCustomerEntrepreneur(ogrnip: String?): EgrnipResponseDto? {
        if (ogrnip.isNullOrBlank()) return null
        val isBusy = checkORGNisBusy(ogrnip)
        val ogrnValidator = OGRNValidator()
        ogrnValidator.validatorType = OGRNValidatorType.OGRNIP_ENTREPRENEUR
        if (!ogrnValidator.isValid(ogrnip, null)) return null
        val client = RestClient.create()
        val url = "$ERNIP_URL?key=$key&ogrn=$ogrnip"
        var result: EgrnipResponseDto? = null
        try {
            result = client.get().uri(url)
                .retrieve()
                .body(EgrnipResponseDto::class.java)
            result?.data?.isBusy = isBusy
        } catch (ex: Exception) {
            logger.error(ex.stackTraceToString())
        }
        return result
    }


}