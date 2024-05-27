package de.ass73.ebt.dms.backend.entities

import jakarta.persistence.*

@Entity
@Table(name = "position")
data class PositionEntity(
    @Id // Generate ID on DB Side
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var name: String
)
