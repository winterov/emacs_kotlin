package ru.emacs.customers.controllers


import jakarta.validation.Valid
import jakarta.validation.Validator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.emacs.customers.dto.request.CustomerCreateDto
import ru.emacs.customers.dto.response.EgrnipResponseDto
import ru.emacs.customers.dto.response.EgrulResponseDto
import ru.emacs.customers.services.CustomersService
import ru.emacs.dtos.AppResponseErrorDto


@RestController
@RequestMapping("/api/v1/customers")
class CustomersControllerApiV1 @Autowired constructor(
    private val customersService: CustomersService,
    private val validator: Validator
    ) {

    @PostMapping("/create")
    fun createCustomer(@RequestBody @Valid dto: CustomerCreateDto): ResponseEntity<Any>{
        val violations = validator.validate(dto)
        val errorMessage: MutableList<String> = ArrayList(4)
        if (violations.isNotEmpty()) {
            for (violation in violations) {
                errorMessage.add(violation.message)
            }
        }
        if(errorMessage.isNotEmpty()) {
            val errorDto = AppResponseErrorDto(HttpStatus.BAD_REQUEST, errorMessage)
            return ResponseEntity(errorDto, HttpStatus.BAD_REQUEST)
        }
        val customer = customersService.createCustomer(dto)
        return ResponseEntity.ok(customer)
    }



    @GetMapping("/load_business")
    fun loadCustomersUL(@RequestParam ogrn:String?,@RequestParam inn: String?): ResponseEntity<EgrulResponseDto>{
        val customer=customersService.loadCustomerBusiness(ogrn,inn)
        if(customer!=null) return ResponseEntity.ok(customer)
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

    @GetMapping("/load_entrepreneur")
    fun loadCustomersIP(@RequestParam ogrnip:String?): ResponseEntity<EgrnipResponseDto>{
        val customer=customersService.loadCustomerEntrepreneur(ogrnip)
        if(customer!=null) return ResponseEntity.ok(customer)
        return ResponseEntity(HttpStatus.NOT_FOUND)
    }

}