package de.ass73.ebt.dms.backend.entities


import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class Efile(
    var name: String,
    var description: String,
    var archive: Boolean,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
)