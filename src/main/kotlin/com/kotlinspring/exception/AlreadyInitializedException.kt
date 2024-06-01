package com.kotlinspring.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

class AlreadyInitializedException(message: String) : RuntimeException(message) {

}
