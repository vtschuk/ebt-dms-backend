package de.ass73.ebt.dms.backend.entities

import jakarta.persistence.*
import java.sql.Date

@Entity
@Table(name = "person")
class PersonEntity(
    @Id // Generate ID on DB Side
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    var vorname: String,
    var name: String,
    var email: String,
    var birthsday: Date,
    var cellphone: String,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    var address: AdressEntity
)
