package de.ass73.ebt.dms.backend.models.auth


class ChangeLoginRequest (
    val password: String,
    val newpassword: String,
    val newpassword2: String
)
