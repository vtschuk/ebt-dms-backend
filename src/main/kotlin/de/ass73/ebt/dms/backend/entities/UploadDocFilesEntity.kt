package de.ass73.ebt.dms.backend.entities

import jakarta.persistence.*

@Entity
@Table(name = "uploaddocfiles")
data class UploadDocFilesEntity(
    @Id // Generate ID on DB Side
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private val id: Long,

    @Column(name = "fileid")
    val fileId: Long,
    val name: String,
    val type: String,

    @Column(name = "filedata", length = 10 * 1024 * 1024)
    val filedata: ByteArray
)
