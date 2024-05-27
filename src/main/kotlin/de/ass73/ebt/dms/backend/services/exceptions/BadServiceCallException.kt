package de.ass73.ebt.dms.backend.services.exceptions

class BadServiceCallException : RuntimeException {
    constructor(message: String?) : super(message)
    constructor(message: String?, throwable: Throwable?) : super(message, throwable)
}