package de.ass73.ebt.dms.backend.models

@NoArgs
data class AdressModel(
    var id: Long,
    var plz: Int,
    var country: String,
    var region: String,
    var ort: String,
    var strasse: String,
    var hausnummer: Int
)
