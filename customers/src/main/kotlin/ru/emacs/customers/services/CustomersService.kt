package ru.emacs.customers.services

import ru.emacs.customers.dto.request.CustomerCreateDto

import ru.emacs.customers.dto.response.EgrnipResponseDto
import ru.emacs.customers.dto.response.EgrulResponseDto
import ru.emacs.customers.aggregators.Customer


interface CustomersService {
    fun createCustomer(dto: CustomerCreateDto): Customer?
    fun loadCustomerBusiness(ogrn: String?, inn: String?): EgrulResponseDto?
    fun loadCustomerEntrepreneur(ogrnip: String?): EgrnipResponseDto?
}