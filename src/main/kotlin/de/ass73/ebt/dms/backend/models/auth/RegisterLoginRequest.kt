package de.ass73.ebt.efile.backend.models.auth

class RegisterLoginRequest(
    val username: String,
    val password: String,
    val role: String,
)