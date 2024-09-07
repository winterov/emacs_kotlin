package ru.emacs.customers.repositories.impl

import org.intellij.lang.annotations.Language
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate

import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository
import ru.emacs.customers.aggregators.Customer
import ru.emacs.customers.repositories.CustomerRepository
import ru.emacs.customers.repositories.extractors.CustomerRowMapper


@Repository
class CustomerRepositoryImpl @Autowired constructor
    (private val jdbcTemplate: NamedParameterJdbcTemplate) : CustomerRepository {
    companion object {
        @Language("sql")
        private const val INSERT_CUSTOMER = "INSERT INTO customers ( type, full_name, abbreviated_name, inn, " +
                "kpp, ogrn, okpo, address, chief_position, chief_fio, passport, status, " +
                "created_at, updated_at) VALUES (:type, :full_name, :abbreviated_name, :inn, :kpp, :ogrn, :okpo, " +
                ":address, :chief_position, :chief_fio, :passport, :status, :createdat, :updatedat)"
        @Language("sql")
        private const val FIND_BY_ID = "SELECT * FROM customers where id = :id"
    }

    override fun findById(id: Long): Customer? {
        val params = MapSqlParameterSource()
        params.addValue("id", id)
        return jdbcTemplate.queryForObject(FIND_BY_ID,params, CustomerRowMapper())
    }

    override fun save(customer: Customer): Customer {
        val keyHolder = GeneratedKeyHolder()
        val params = MapSqlParameterSource()
        val id = arrayOf("id")
        params.addValue("type", customer.type.toString())
        params.addValue("full_name",customer.fullName)
        params.addValue("abbreviated_name",customer.abbreviatedName)
        params.addValue("inn",customer.inn)
        params.addValue("kpp",customer.kpp)
        params.addValue("ogrn",customer.ogrn)
        params.addValue("okpo",customer.okpo)
        params.addValue("address",customer.address)
        params.addValue("chief_position",customer.chiefPosition)
        params.addValue("chief_fio",customer.chiefFio)
        params.addValue("passport",customer.passport)
        params.addValue("status",customer.status.toString())
        params.addValue("createdat",customer.createdAt)
        params.addValue("updatedat",customer.updatedAt)
        jdbcTemplate.update(INSERT_CUSTOMER,params,keyHolder,id)
        customer.id = keyHolder.key as Long
        return customer
    }

}