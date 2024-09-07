package ru.emacs.customers.repositories.extractors


import org.springframework.jdbc.core.RowMapper
import ru.emacs.customers.aggregators.Customer
import ru.emacs.customers.aggregators.CustomerType
import ru.emacs.entities.EntitiesStatus
import java.sql.ResultSet

class CustomerRowMapper:RowMapper<Customer> {

    override fun mapRow(rs: ResultSet, rowNum: Int): Customer {
        val id = rs.getLong("id")
        val type = CustomerType.valueOf(rs.getString("type"))
        val fullName = rs.getString("full_name")
        val parentId=rs.getLong("parent_id")
        val abbreviatedName = rs.getString("abbreviated_name")
        val inn = rs.getString("inn")
        val kpp = rs.getString("kpp")
        val ogrn = rs.getString("ogrn")
        val okpo = rs.getString("okpo")
        val address = rs.getString("address")
        val chiefPosition = rs.getString("chief_position")
        val chiefFio = rs.getString("chief_fio")
        val passport = rs.getString("passport")
        val status = EntitiesStatus.valueOf(rs.getString("status"))
        val createdAt = rs.getTimestamp("created_at").toLocalDateTime()
        val updatedAt = rs.getTimestamp("updated_at").toLocalDateTime()
        return Customer(id,parentId,type,fullName,abbreviatedName,inn,kpp,ogrn,okpo,address,chiefPosition,chiefFio,passport,status,createdAt,updatedAt)
    }
}