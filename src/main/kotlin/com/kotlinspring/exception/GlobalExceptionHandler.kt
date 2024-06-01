package com.kotlinspring.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.ErrorResponse
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExistsException(ex: UserAlreadyExistsException): ResponseEntity<String>{
        return ResponseEntity(ex.message, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(AlreadyInitializedException::class)
    fun handleCashAlreadyInitializedException(ex: AlreadyInitializedException): ResponseEntity<ErrorResponse>{

        val errorResponse = ErrorResponse(
            status = HttpStatus.BAD_REQUEST.value(),
            message = ex.message ?: "Cash register has already been initialized! Try deposit instead"
        )

        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)    }

    data class ErrorResponse(
        val status: Int,
        val message: String
    )
}