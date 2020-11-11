package com.acme.videorental.infrastructure.exception

import com.acme.videorental.domain.model.customer.CustomerAlreadyExistingException
import com.acme.videorental.domain.model.customer.CustomerNotFoundException
import com.acme.videorental.domain.model.film.FilmAlreadyExistingException
import com.acme.videorental.domain.model.film.FilmNotFoundException
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
