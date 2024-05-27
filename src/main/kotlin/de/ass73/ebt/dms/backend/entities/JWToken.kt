package de.ass73.ebt.dms.backend.entities

import jakarta.persistence.*

@Entity
data class JWToken(
    @Id // Generate ID on DB Side
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(unique = true)
    var token: String,

    @Enumerated(EnumType.STRING)
    var tokenType: TokenType = TokenType.BEARER,
    var revoked: Boolean,
    var expired: Boolean,

    @ManyToOne
    @JoinColumn(name = "userentity_id")
    var userEntity: UserEntity,
)
