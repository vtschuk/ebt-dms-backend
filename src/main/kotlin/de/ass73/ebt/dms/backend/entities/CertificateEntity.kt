package de.ass73.ebt.dms.backend.entities

import jakarta.persistence.*


@Entity
@Table(name = "certificate")
data class CertificateEntity(
    @Id
    @Column(name = "id") // Generate ID on DB Side
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var name: String
)
