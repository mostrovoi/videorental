package com.acme.videorental.sharedKernel.infrastructure.exception

import com.acme.videorental.customers.domain.CustomerAlreadyExistingException
import com.acme.videorental.customers.domain.CustomerNotFoundException
import com.acme.videorental.purchases.domain.FilmAlreadyExistingException
import com.acme.videorental.purchases.domain.FilmNotFoundException
import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import javax.servlet.http.HttpServletRequest


@ControllerAdvice
class GlobalControllerExceptionHandler {

    private val logger = KotlinLogging.logger {}

    @ExceptionHandler(CustomerNotFoundException::class, FilmNotFoundException::class)
    fun handleEntityNotFoundException(e: Exception, request: HttpServletRequest): ResponseEntity<Any> {
        logger.error("${request.requestURI} throws ${e.message}")
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.message)
    }

    @ExceptionHandler(FilmAlreadyExistingException::class, CustomerAlreadyExistingException::class)
    fun handleDuplicateEntityException(e: Exception, request: HttpServletRequest): ResponseEntity<Any> {
        logger.error("${request.requestURI} throws ${e.message}")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleWrongArgumentsException(e: Exception, request: HttpServletRequest): ResponseEntity<Any> {
        logger.error("${request.requestURI} throws ${e.message}")
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
    }
}
