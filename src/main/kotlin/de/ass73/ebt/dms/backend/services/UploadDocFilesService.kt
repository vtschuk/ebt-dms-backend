package de.ass73.ebt.dms.backend.services

import de.ass73.ebt.dms.backend.entities.UploadDocFilesEntity
import de.ass73.ebt.dms.backend.models.UploadDocFilesModel
import de.ass73.ebt.dms.backend.models.UploadDocFilesResponseModel
import de.ass73.ebt.dms.backend.repository.UploadDocFilesRepo
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.util.*
import java.util.stream.Collectors

@Service
class UploadDocFilesService(
    @Autowired
    private val uploadDocFilesRepo: UploadDocFilesRepo,

    @Autowired
    private val modelMapper: ModelMapper,

    ) {
    fun getAllUploadDocFiles(username: String): List<UploadDocFilesModel> {
        return uploadDocFilesRepo.findAll().stream()
            .map { entity -> modelMapper.map(entity, UploadDocFilesModel::class.java) }
            ?.collect(Collectors.toList()) ?: Collections.emptyList()
    }

    fun getUploadDocFilesByPersonID(id: Long, username: String): List<UploadDocFilesModel> {
        return uploadDocFilesRepo.findByPersonId(id)
            .stream()
            .map { entity -> modelMapper.map(entity, UploadDocFilesModel::class.java) }
            ?.collect(Collectors.toList()) ?: Collections.emptyList()
    }

    @Throws(IOException::class)
    fun createUploadFile(
        personId: Long,
        multipartFile: MultipartFile,
        username: String
    ): UploadDocFilesResponseModel {
        val entity: UploadDocFilesEntity = UploadDocFilesEntity(
            0L,
            personId,
            multipartFile.originalFilename!!,
            multipartFile.contentType!!,
            multipartFile.bytes
        )
        uploadDocFilesRepo.save(entity)
        return UploadDocFilesResponseModel("Upload successfull")
    }

    fun deleteUploadFileById(id: Long, username: String?) {
        uploadDocFilesRepo.deleteById(id)
    }

    fun getUploadFile(id: Long, username: String): UploadDocFilesModel {
        val entity: Optional<UploadDocFilesEntity?> = uploadDocFilesRepo.findById(id)
        return modelMapper.map(
            entity.orElseThrow { RuntimeException("keine Daten für Docfile gefunden") },
            UploadDocFilesModel::class.java
        )
    }
}
