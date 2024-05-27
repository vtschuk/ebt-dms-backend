package de.ass73.ebt.dms.backend.entities

import jakarta.persistence.*

@Entity
@Table(name = "address")
data class AdressEntity(
    @Id // Generate ID on DB Side
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var plz: Int,
    var country: String,
    var region: String,
    var ort: String,
    var strasse: String,
    var hausnummer: Int
)
