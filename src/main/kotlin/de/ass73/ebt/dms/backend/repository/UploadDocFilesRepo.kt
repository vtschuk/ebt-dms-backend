package de.ass73.ebt.dms.backend.repository

import de.ass73.ebt.dms.backend.entities.UploadDocFilesEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UploadDocFilesRepo : JpaRepository<UploadDocFilesEntity, Long> {
    fun findByFileId(fileId: Long): List<UploadDocFilesEntity>
}
