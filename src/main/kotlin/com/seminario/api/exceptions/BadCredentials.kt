package com.seminario.api.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import kotlin.RuntimeException

@ResponseStatus(HttpStatus.FORBIDDEN)
class BadCredentials(message: String?): RuntimeException(message)