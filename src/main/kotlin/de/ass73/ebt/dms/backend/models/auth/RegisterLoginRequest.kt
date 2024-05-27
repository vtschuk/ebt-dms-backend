package de.ass73.ebt.dms.backend.models.auth

class RegisterLoginRequest(
    val username: String,
    val password: String,
    val role: String,
)