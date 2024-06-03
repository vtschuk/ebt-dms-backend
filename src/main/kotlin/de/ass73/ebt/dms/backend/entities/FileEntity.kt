package de.ass73.ebt.dms.backend.entities

import jakarta.persistence.*
import java.sql.Date

@Entity
@Table(name = "fileentity")
class FileEntity(
    @Id // Generate ID on DB Side
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,
    var name: String,
    var date: Date,
    var issue: String,
    var description: String,
    var archive: Boolean,
    var encrypted: Boolean
)
