package ru.emacs.customers.repositories

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository


import ru.emacs.customers.aggregators.Customer
import ru.emacs.entities.EntitiesStatus



@Repository
interface CustomerJdbcRepository: CrudRepository<Customer, Long> {
    fun countByOgrnAndStatus(ogrn:String,status: EntitiesStatus):Int
    fun countByInnAndStatus(inn:String,status:EntitiesStatus):Int
}