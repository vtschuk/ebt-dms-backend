package de.ass73.ebt.dms.backend.services

import de.ass73.ebt.dms.backend.models.FileModel

interface FileApiServiceInterface {
    fun getAllFiles(username: String): List<FileModel>
    fun getFileById(id: Long, username: String): FileModel
    fun create(fileModel: FileModel, username: String): FileModel
    fun save(id: Long, fileModel: FileModel, username: String): FileModel
    fun delete(id: Long, username: String): FileModel
}
