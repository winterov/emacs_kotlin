package ru.emacs.customers.validators

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import ru.emacs.customers.aggregators.CustomerType
import ru.emacs.customers.dto.request.CustomerCreateDto
import ru.emacs.validators.*

class CustomerValidator : ConstraintValidator<CustomerDto, CustomerCreateDto> {

    override fun isValid(dto: CustomerCreateDto, ctx: ConstraintValidatorContext?): Boolean {
        val errorMessages: MutableList<String> = ArrayList()
        if (dto.type == null) {
            return false
        }
        when (dto.type) {
            CustomerType.GROUP -> validateGroup(dto, ctx,errorMessages)
            CustomerType.HUMAN -> validateHuman(dto, ctx,errorMessages)
            CustomerType.BUSINESS -> validateBusiness(dto, ctx,errorMessages)
            CustomerType.ENTREPRENEUR -> validateEntrepreneur(dto, ctx,errorMessages)
        }
        if (errorMessages.size > 0) {
            ctx?.buildConstraintViolationWithTemplate(errorMessages.toString())
            return false
        }
        return true
    }

    private fun validateBusiness(dto: CustomerCreateDto, ctx: ConstraintValidatorContext?,errorMessages: MutableList<String>) {
        validateString(dto.fullName, "Полное имя", 350, errorMessages)
        validateString(dto.abbreviatedName, "Сокращенное имя", 350, errorMessages)
        validateString(dto.address, "Адрес", 400, errorMessages)
        validateString(dto.chiefPosition, "Должность", 200, errorMessages)
        validateString(dto.chiefFio, "Фамилия И.О.", 200, errorMessages)
        val innValidator = INNValidator()
        innValidator.validatorType = INNValidatorType.INN_BUSINESS
        if (!innValidator.isValid(dto.inn, ctx)) {
            errorMessages.add("Поле 'ИНН' не валидно")
        }
        val ogrnValidator = OGRNValidator()
        ogrnValidator.validatorType = OGRNValidatorType.OGRN_BUSINESS
        if (!ogrnValidator.isValid(dto.ogrn, ctx)) {
            errorMessages.add("Поле 'ОГРН' не валидно")
        }
        val kppValidator = KPPValidator()
        if (!kppValidator.isValid(dto.kpp, ctx)) {
            errorMessages.add("Поле 'КПП' не валидно")
        }
        validateOKPO(dto.okpo,errorMessages)
        if(!dto.passport.isNullOrEmpty()){
            errorMessages.add("Поле 'Паспорт' должно быть пустым")
        }
    }

    private fun validateEntrepreneur(dto: CustomerCreateDto, ctx: ConstraintValidatorContext?,errorMessages: MutableList<String>) {
        validateString(dto.fullName, "Полное имя", 350, errorMessages)
        validateString(dto.abbreviatedName, "Сокращенное имя", 350, errorMessages)
        validateString(dto.address, "Адрес", 400, errorMessages)
        val innValidator = INNValidator()
        innValidator.validatorType = INNValidatorType.INN_HUMAN
        if (!innValidator.isValid(dto.inn, ctx)) {
            errorMessages.add("Поле 'ИНН' не валидно")
        }
        val ogrnValidator = OGRNValidator()
        ogrnValidator.validatorType = OGRNValidatorType.OGRNIP_ENTREPRENEUR
        if (!ogrnValidator.isValid(dto.ogrn, ctx)) {
            errorMessages.add("Поле 'ОГРН' не валидно")
        }
        val kppValidator = KPPValidator()
        if (!kppValidator.isValid(dto.kpp, ctx)) {
            errorMessages.add("Поле 'КПП' не валидно")
        }
        validateOKPO(dto.okpo,errorMessages)
        if(!dto.passport.isNullOrEmpty()){
            errorMessages.add("Поле 'Паспорт' должно быть пустым")
        }
        if(!dto.chiefFio.isNullOrEmpty()){
            errorMessages.add("Поле 'Фамилия И.О.' должно быть пустым")
        }
        if(!dto.chiefPosition.isNullOrEmpty()){
            errorMessages.add("Поле 'Должность' должно быть пустым")
        }
    }

    private fun validateHuman(dto: CustomerCreateDto, ctx: ConstraintValidatorContext?,errorMessages: MutableList<String>) {

    }

    private fun validateGroup(dto: CustomerCreateDto, ctx: ConstraintValidatorContext?,errorMessages: MutableList<String>) {
        validateString(dto.fullName, "Полное имя", 350, errorMessages)
    }

    private fun validateString(
        fullName: String?,
        fieldName: String,
        constraintLength: Int,
        errorMessages: MutableList<String>
    ) {
        if (fullName.isNullOrEmpty()) {
            errorMessages.add("Поле '$fieldName' не может быть пустым")
            return
        }
        if (fullName.length > constraintLength) {
            errorMessages.add("Поле '$fieldName' максимальный размер $constraintLength символов")
        }
        return
    }
    private fun validateOKPO(okpo:String?, errorMessages: MutableList<String>) {
        if (okpo.isNullOrEmpty()) {
            errorMessages.add("Поле 'ОКПО' не может быть пустым")
            return
        }
        if (okpo.length != 8 && okpo.length != 10) {
            errorMessages.add("Поле 'ОКПО'должно быть 8 или 10 символов")
        }
        return
    }
}