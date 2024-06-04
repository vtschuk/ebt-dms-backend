package de.ass73.ebt.dms.backend.models

@NoArgs
data class UserModel(
    var id: Long,
    var firstName: String,
    var lastName: String,
    var email: String,
    var username2: String,
    var password2: String,
    var role: String
)