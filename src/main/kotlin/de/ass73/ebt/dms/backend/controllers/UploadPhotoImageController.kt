package de.ass73.ebt.dms.backend.controllers

import de.ass73.ebt.dms.backend.entities.image.PhotoImage
import de.ass73.ebt.efile.backend.models.image.PhotoUploadResponse
import de.ass73.ebt.dms.backend.services.image.UploadPhotoImageService
import de.ass73.ebt.dms.backend.services.users.LoginTools
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException

@RestController
@RequestMapping("/uploadphoto")
class UploadPhotoImageController(
    @Autowired
    private val photoImageService: UploadPhotoImageService,
    @Autowired
    private val loginTools: LoginTools

) {
    @ResponseBody
    @PostMapping("/{fileId}")
    @CrossOrigin(origins = ["http://localhost:4200"])
    @Throws(IOException::class)
    fun upload(
        @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String, @PathVariable fileId: Long?, @RequestParam("file") file: MultipartFile
    ): ResponseEntity<PhotoUploadResponse> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        val response: PhotoUploadResponse? = fileId?.let { photoImageService.uploadImage(it, file, username) }

        return ResponseEntity<PhotoUploadResponse>(response, HttpStatus.OK)
    }

    @GetMapping("/{fileId}")
    @CrossOrigin(origins = ["http://localhost:4200"])
    fun getImageByFileId(
        @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String, @PathVariable("fileId") fileId: Long
    ): ResponseEntity<PhotoImage> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        val image: PhotoImage = photoImageService.getImageByFileId(fileId, username)

        return ResponseEntity<PhotoImage>(image, HttpStatus.OK)

    }
}
