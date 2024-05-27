package de.ass73.ebt.efile.backend.models


import java.sql.Date

@NoArgs
data class PersonModel(
    var id: Long,
    var vorname: String,
    var name: String,
    var email: String,
    var birthsday: Date,
    var cellphone: String,
    //var photo: ByteArray,
    var address: AdressModel
)

