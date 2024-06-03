package de.ass73.ebt.dms.backend.services

import de.ass73.ebt.dms.backend.entities.FileEntity
import de.ass73.ebt.dms.backend.models.FileModel
import de.ass73.ebt.dms.backend.repository.DBRepoFileInterface
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class FileApiService(
    @Autowired
    private val dbRepoFileInterface: DBRepoFileInterface,
    @Autowired
    private val mapper: ModelMapper
) : FileApiServiceInterface {

    override fun getAllFiles(username: String): List<FileModel> {
        return dbRepoFileInterface.findAll().stream()
            .map { personEntity -> mapper.map(personEntity, FileModel::class.java) }
            .collect(Collectors.toList())
    }

    override fun getFileById(id: Long, username: String): FileModel {
        val fileEntity: FileEntity = dbRepoFileInterface.findById(id).orElseThrow()
        //  .orElseThrow{ ex -> NoContentException("Kann keine Akte mit der ID $id finden") }

        return mapper.map(fileEntity, FileModel::class.java)
    }

    override fun create(fileModel: FileModel, username: String): FileModel {
        var fileEntity: FileEntity = mapper.map(fileModel, FileEntity::class.java)
        fileEntity = dbRepoFileInterface.save(fileEntity)
        return mapper.map(fileEntity, FileModel::class.java)
    }

    //todo: make it immutable
    override fun save(id: Long, fileModel: FileModel, username: String): FileModel {
        val fileEntity: FileEntity = mapper.map(fileModel, FileEntity::class.java)
        var fileEntityDB: FileEntity = dbRepoFileInterface.findById(id).orElseThrow()
        //.orElseThrow { NoContentException("Kann keinen Eintrag mit der ID: $id finden") }
        fileEntityDB.name = fileEntity.name
        fileEntityDB = dbRepoFileInterface.save(fileEntityDB)
        return mapper.map(fileEntityDB, FileModel::class.java)
    }

    override fun delete(id: Long, username: String): FileModel {
        println("Delete Person with id $id")
        val fileEntity: FileEntity = dbRepoFileInterface.getReferenceById(id)
        dbRepoFileInterface.deleteById(id)
        return mapper.map(fileEntity, FileModel::class.java)
    }

}

