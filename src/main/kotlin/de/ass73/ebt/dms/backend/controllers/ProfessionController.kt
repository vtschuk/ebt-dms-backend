package de.ass73.ebt.dms.backend.controllers

import de.ass73.ebt.dms.backend.models.ProfessionModel
import de.ass73.ebt.dms.backend.services.ProfessionService
import de.ass73.ebt.dms.backend.services.users.LoginTools
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/profession")
class ProfessionController(
    @Autowired
    private val professionService: ProfessionService,
    @Autowired
    private val loginTools: LoginTools
) {
    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping(value = ["/all"], produces = ["application/json"])
    fun getAllProfessions(
        @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<List<ProfessionModel>> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<List<ProfessionModel>>(professionService.getAllProfessions(username), HttpStatus.OK)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping(value = ["/{id}"], produces = ["application/json"])
    fun getProfessionById(
        @PathVariable id: Long, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<ProfessionModel> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<ProfessionModel>(professionService.getProfessionById(id, username), HttpStatus.OK)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping(value = [""], produces = ["application/json"])
    fun createNewProfession(
        @RequestBody professionModel: ProfessionModel?, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<ProfessionModel> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<ProfessionModel>(
            professionModel?.let { professionService.createProfession(it, username) },
            HttpStatus.CREATED
        )
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PutMapping(value = ["/{id}"], produces = ["application/json"])
    fun saveProfession(
        @PathVariable id: Long, @RequestBody professionModel: ProfessionModel?, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<ProfessionModel> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<ProfessionModel>(
            professionService.save(id, professionModel, username),
            HttpStatus.CREATED
        )
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @DeleteMapping(value = ["/{id}"], produces = ["application/json"])
    fun deleteProfessionById(
        @PathVariable id: Long?, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<Void> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        if (id != null) {
            professionService.delete(id, username)
        }
        return ResponseEntity<Void>(HttpStatus.NO_CONTENT)
    }

}
