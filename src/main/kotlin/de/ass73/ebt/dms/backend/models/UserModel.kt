package de.ass73.ebt.efile.backend.models

@NoArgs
data class UserModel(
    var id: Long,
    var username2: String,
    var password2: String,
    var role: String
)