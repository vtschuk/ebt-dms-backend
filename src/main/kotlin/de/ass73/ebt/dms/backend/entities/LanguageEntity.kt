package de.ass73.ebt.dms.backend.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table


@Entity
@Table(name = "language")
data class LanguageEntity(
    @Id
    @Column(name = "id")
    var id: Long,
    var name: String,
    var aktiv: Boolean
)

