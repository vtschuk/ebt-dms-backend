/**
 * Controller for Upload Document Files of any Person from the List
 */
package de.ass73.ebt.dms.backend.controllers

import de.ass73.ebt.efile.backend.models.UploadDocFilesModel
import de.ass73.ebt.efile.backend.models.UploadDocFilesResponseModel
import de.ass73.ebt.dms.backend.services.UploadDocFilesService
import de.ass73.ebt.dms.backend.services.users.LoginTools
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@RestController
@RequestMapping("/uploaddoc")
class UploadDocFilesController(
    @Autowired
    private val uploadDocFilesService: UploadDocFilesService,
    @Autowired
    private val loginTools: LoginTools
) {
    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping(value = ["/all"], produces = ["application/json"])
    fun getAllUploadDocFiles(
        @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<List<UploadDocFilesModel>> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<List<UploadDocFilesModel>>(
            uploadDocFilesService.getAllUploadDocFiles(username),
            HttpStatus.FOUND
        )
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping(value = ["/all/{personId}"], produces = ["application/json"])
    fun getUploadDocFilesByPersonID(
        @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String, @PathVariable personId: Long?
    ): ResponseEntity<List<UploadDocFilesModel>> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<List<UploadDocFilesModel>>(
            personId?.let { uploadDocFilesService.getUploadDocFilesByPersonID(it, username) },
            HttpStatus.OK
        )
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping(value = ["/{id}"], produces = ["application/json"])
    @ResponseBody
    fun getUploadDocFile(
        @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String, @PathVariable id: Long
    ): ResponseEntity<*> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        val model: UploadDocFilesModel = uploadDocFilesService.getUploadFile(id, username)
        return ResponseEntity.status(HttpStatus.OK)
            //todo
            //.contentType(MediaType(model.type))
            .body(model.filedata)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping(value = ["/{personId}"])
    @Throws(IOException::class)
    fun createUploadDocFile(
        @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String, @PathVariable personId: Long, @RequestParam("file") multipartFile: MultipartFile
    ): ResponseEntity<UploadDocFilesResponseModel> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<UploadDocFilesResponseModel>(
            uploadDocFilesService.createUploadFile(personId, multipartFile, username),
            HttpStatus.CREATED
        )
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @DeleteMapping(value = ["/{id}"], produces = ["application/json"])
    fun deleteUpdateById(
        @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String, @PathVariable id: Long?
    ): ResponseEntity<Void> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        if (id != null) {
            uploadDocFilesService.deleteUploadFileById(id, username)
        }
        return ResponseEntity<Void>(HttpStatus.NO_CONTENT)
    }

}
