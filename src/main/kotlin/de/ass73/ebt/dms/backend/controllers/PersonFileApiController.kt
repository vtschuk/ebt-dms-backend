package de.ass73.ebt.dms.backend.controllers

import de.ass73.ebt.efile.backend.models.PersonModel
import de.ass73.ebt.dms.backend.services.PersonFileApiServiceInterface
import de.ass73.ebt.dms.backend.services.users.LoginTools
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/file")
class PersonFileApiController(
    @Autowired
    var serviceInterface: PersonFileApiServiceInterface,

    @Autowired
    private val loginTools: LoginTools,
) {
    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping(value = ["/all"], produces = ["application/json"])
    fun getAllPersons(
        @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<List<PersonModel>> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity(serviceInterface.getAllPersons(username), HttpStatus.OK)
    }


    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping(value = ["/get/{id}"], produces = ["application/json"])
    fun getPersonById(
        @PathVariable id: Long, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<PersonModel> {
        //logger.info("get Persind with id: $id")
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<PersonModel>(serviceInterface.getPersonById(id, username), HttpStatus.OK)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping(value = ["/create"], consumes = [MediaType.APPLICATION_JSON_VALUE], produces = ["application/json"])
    fun createNewPerson(
        @RequestBody personModel: PersonModel,
        @RequestHeader(HttpHeaders.AUTHORIZATION) autorization: String
    ): ResponseEntity<PersonModel> {
        //logger.info("create Person")
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<PersonModel>(serviceInterface.create(personModel, username), HttpStatus.CREATED)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PutMapping(value = ["/save/{id}"], produces = ["application/json"])
    fun savePerson(
        @PathVariable id: Long, @RequestBody personModel: PersonModel, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<PersonModel> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<PersonModel>(serviceInterface.save(id, personModel, username), HttpStatus.CREATED)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @DeleteMapping(value = ["/delete/{id}"], produces = ["application/json"])
    fun deletePerson(
        @PathVariable id: Long, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<PersonModel> {
        //logger.info("delete Person with id: $id")
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<PersonModel>(serviceInterface.delete(id, username), HttpStatus.NO_CONTENT)
    }

}
