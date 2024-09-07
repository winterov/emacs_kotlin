package ru.emacs.exception


import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import ru.emacs.dtos.AppResponseErrorDto

@ControllerAdvice
class GlobalExceptionAdvice {
    private val logger = LoggerFactory.getLogger(GlobalExceptionAdvice::class.java)
    @ExceptionHandler
    fun catchResourceNotFoundException(e: RuntimeException): AppResponseErrorDto {
        logger.error(e.message, e)
        return AppResponseErrorDto(HttpStatus.BAD_REQUEST, e.localizedMessage)
    }
}