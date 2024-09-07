package ru.emacs.customers.repositories

import ru.emacs.customers.aggregators.Customer


interface CustomerRepository {
    fun findById(id: Long): Customer?
    fun save(customer: Customer):Customer
}