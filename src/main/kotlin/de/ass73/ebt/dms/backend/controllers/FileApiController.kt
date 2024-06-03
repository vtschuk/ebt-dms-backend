package de.ass73.ebt.dms.backend.controllers

import de.ass73.ebt.dms.backend.models.FileModel
import de.ass73.ebt.dms.backend.services.FileApiServiceInterface
import de.ass73.ebt.dms.backend.services.users.LoginTools
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/file")
class FileApiController(
    @Autowired
    var serviceInterface: FileApiServiceInterface,

    @Autowired
    private val loginTools: LoginTools,
) {
    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping(value = ["/all"], produces = ["application/json"])
    fun getAllFiles(
        @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<List<FileModel>> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity(serviceInterface.getAllFiles(username), HttpStatus.OK)
    }


    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping(value = ["/get/{id}"], produces = ["application/json"])
    fun getFileById(
        @PathVariable id: Long, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<FileModel> {
        //logger.info("get Persind with id: $id")
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<FileModel>(serviceInterface.getFileById(id, username), HttpStatus.OK)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping(value = ["/create"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = ["application/json"])
    fun createNewFile(
        @RequestBody fileModel: FileModel,
        @RequestHeader(HttpHeaders.AUTHORIZATION) autorization: String
    ): ResponseEntity<FileModel> {
        //logger.info("create Person")
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<FileModel>(serviceInterface.create(fileModel, username), HttpStatus.CREATED)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PutMapping(value = ["/save/{id}"], produces = ["application/json"])
    fun saveFile(
        @PathVariable id: Long, @RequestBody fileModel: FileModel, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<FileModel> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<FileModel>(serviceInterface.save(id, fileModel, username), HttpStatus.CREATED)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @DeleteMapping(value = ["/delete/{id}"], produces = ["application/json"])
    fun deleteFile(
        @PathVariable id: Long, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<FileModel> {
        //logger.info("delete Person with id: $id")
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<FileModel>(serviceInterface.delete(id, username), HttpStatus.NO_CONTENT)
    }

}
