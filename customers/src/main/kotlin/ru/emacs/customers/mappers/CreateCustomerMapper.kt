package ru.emacs.customers.mappers

import ru.emacs.customers.dto.request.CustomerCreateDto
import ru.emacs.customers.aggregators.Customer

import ru.emacs.entities.EntitiesStatus
import java.time.LocalDateTime


class CreateCustomerMapper {
    companion object {
        fun fromDto(
            dto: CustomerCreateDto,
            updated: LocalDateTime,
            created: LocalDateTime
        ): Customer {
            return Customer(
                null,
                dto.parentId,
                dto.type!!,
                dto.fullName!!,
                dto.abbreviatedName,
                dto.inn,
                dto.kpp,
                dto.ogrn,
                dto.okpo,
                dto.address,
                dto.chiefPosition,
                dto.chiefFio,
                null,
                EntitiesStatus.ACTIVE,
                created,
                updated
            )
        }

    }

}