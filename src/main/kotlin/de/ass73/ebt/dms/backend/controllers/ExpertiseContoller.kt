package de.ass73.ebt.dms.backend.controllers

import de.ass73.ebt.efile.backend.models.ExpertiseModel
import de.ass73.ebt.dms.backend.services.ExpertiseService
import de.ass73.ebt.dms.backend.services.users.LoginTools
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/expertise")
class ExpertiseContoller {

    @Autowired
    private lateinit var expertiseService: ExpertiseService

    @Autowired
    private lateinit var loginTools: LoginTools

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping(value = ["/all"], produces = ["application/json"])
    fun getAllExpertises(
        @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<List<ExpertiseModel>> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<List<ExpertiseModel>>(expertiseService.getAllExpertises(username), HttpStatus.OK)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping(value = ["/{id}"], produces = ["application/json"])
    fun getExpertiseById(
        @PathVariable id: Long, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<ExpertiseModel> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<ExpertiseModel>(expertiseService.getExpertiseById(id, username), HttpStatus.OK)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping(value = [""], produces = ["application/json"])
    fun createNewExpertise(
        @RequestBody expertiseModel: ExpertiseModel, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<ExpertiseModel> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<ExpertiseModel>(
            expertiseService.createExpertise(expertiseModel, username),
            HttpStatus.CREATED
        )
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PutMapping(value = ["/{id}"], produces = ["application/json"])
    fun saveExpertise(
        @PathVariable id: Long, @RequestBody expertiseModel: ExpertiseModel?, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<ExpertiseModel> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<ExpertiseModel>(
            expertiseService.saveExpertise(id, expertiseModel, username),
            HttpStatus.CREATED
        )
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @DeleteMapping(value = ["/{id}"], produces = ["application/json"])
    fun deleteExpertiseById(
        @PathVariable id: Long?, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<Void> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        if (id != null) {
            expertiseService.deleteExpertise(id, username)
        }
        return ResponseEntity<Void>(HttpStatus.NO_CONTENT)
    }

}
