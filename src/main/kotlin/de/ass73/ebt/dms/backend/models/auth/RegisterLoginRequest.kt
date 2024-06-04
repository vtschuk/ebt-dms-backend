package de.ass73.ebt.dms.backend.models.auth

class RegisterLoginRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val username: String,
    val password: String,
    val role: String,
)