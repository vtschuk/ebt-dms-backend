package de.ass73.ebt.dms.backend.entities.image

import jakarta.persistence.*

@Entity
@Table(name = "photoimage")
data class PhotoImage(
    @Id // Generate ID on DB Side
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long,

    @Column(name = "fileid")
    var fileId: Long,
    var name: String,
    var type: String,
    @Column(name = "imagedata", length = 10 * MBYTE)
    var imageData: ByteArray
) {
    companion object {
        private const val MBYTE = 1024 * 1024
    }
}
