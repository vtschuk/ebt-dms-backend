package de.ass73.ebt.dms.backend.services.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.time.ZonedDateTime

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResourceNotFoundException(
    override val message: String,
    val throwable: Throwable,
    val httpStatus: HttpStatus,
    val timestamp: ZonedDateTime
) : RuntimeException()
