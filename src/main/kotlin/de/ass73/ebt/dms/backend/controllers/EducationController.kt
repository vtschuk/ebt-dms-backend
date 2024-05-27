package de.ass73.ebt.dms.backend.controllers

import de.ass73.ebt.dms.backend.models.EducationModel
import de.ass73.ebt.dms.backend.services.EducationService
import de.ass73.ebt.dms.backend.services.users.LoginTools
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/education")
class EducationController(
    @Autowired
    private val educationService: EducationService,

    @Autowired
    private val loginTools: LoginTools
) {


    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping(value = ["/all"], produces = ["application/json"])
    fun getAllEducations(
        @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<List<EducationModel>> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<List<EducationModel>>(educationService.getAllEducations(username), HttpStatus.OK)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping(value = ["/{id}"], produces = ["application/json"])
    fun getEducationById(
        @PathVariable id: Long, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<EducationModel> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<EducationModel>(educationService.getEducationById(id, username), HttpStatus.OK)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping(value = [""], produces = ["application/json"])
    fun createNewEducation(
        @RequestBody educationModel: EducationModel?, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<EducationModel> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<EducationModel>(
            educationService.createEducation(educationModel, username),
            HttpStatus.CREATED
        )
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PutMapping(value = ["/{id}"], produces = ["application/json"])
    fun saveEducation(
        @PathVariable id: Long, @RequestBody educationModel: EducationModel?, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<EducationModel> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<EducationModel>(
            educationService.saveEducation(id, educationModel, username),
            HttpStatus.CREATED
        )
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @DeleteMapping(value = ["/{id}"], produces = ["application/json"])
    fun deleteEducationById(
        @PathVariable id: Long?, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<Void> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        if (id != null) {
            educationService.delete(id, username)
        }
        return ResponseEntity<Void>(HttpStatus.NO_CONTENT)
    }
}
