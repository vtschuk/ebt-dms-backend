package de.ass73.ebt.dms.backend.services.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(value = HttpStatus.NO_CONTENT)
class NoContentException(error: String?) : RuntimeException(error)
