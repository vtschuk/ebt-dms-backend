package de.ass73.ebt.dms.backend.repository.image

import de.ass73.ebt.dms.backend.entities.image.PhotoImage
import org.springframework.data.jpa.repository.JpaRepository

interface UploadPhotoImageRepository : JpaRepository<PhotoImage, Long> {
    fun findByFileId(fileId: Long): List<PhotoImage?>
}
