package ru.emacs.customers.aggregators

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import ru.emacs.entities.EntitiesStatus
import java.time.LocalDateTime

@Table("customers")
data class Customer (
    @field:Id
    var id: Long?,
    val parentId:Long,
    val type: CustomerType,
    val fullName: String,
    val abbreviatedName:String?,
    val inn:String?,
    val kpp:String?,
    val ogrn:String?,
    val okpo:String?,
    val address:String?,
    val chiefPosition:String?,
    val chiefFio:String?,
    val passport:String?,
    val status: EntitiesStatus,
    val createdAt:LocalDateTime,
    val updatedAt:LocalDateTime
)
enum class CustomerType{
    BUSINESS,ENTREPRENEUR,HUMAN,GROUP
}