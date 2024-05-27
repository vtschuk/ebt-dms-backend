package de.ass73.ebt.dms.backend.controllers

import de.ass73.ebt.efile.backend.models.PositionModel
import de.ass73.ebt.dms.backend.services.PositionService
import de.ass73.ebt.dms.backend.services.users.LoginTools
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/position")
class PositionController(
    @Autowired
    private val positionService: PositionService,

    @Autowired
    private val loginTools: LoginTools,
) {

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping(value = ["/all"], produces = ["application/json"])
    fun getAllPositions(
        @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<List<PositionModel>> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<List<PositionModel>>(positionService.getAllPositions(username), HttpStatus.OK)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping(value = ["/{id}"], produces = ["application/json"])
    fun getPositionById(
        @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String, @PathVariable id: Long
    ): ResponseEntity<PositionModel> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<PositionModel>(positionService.getPositionById(id, username), HttpStatus.OK)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping(value = [""], produces = ["application/json"])
    fun createNewPosition(
        @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String, @RequestBody positionModel: PositionModel
    ): ResponseEntity<PositionModel> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        println("recieved: ${positionModel.id}, ${positionModel.name}")
        return ResponseEntity<PositionModel>(
            positionService.createPosition(positionModel, username),
            HttpStatus.CREATED
        )
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PutMapping(value = ["/{id}"], produces = ["application/json"])
    fun savePosition(
        @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String, @PathVariable id: Long, @RequestBody positionModel: PositionModel?
    ): ResponseEntity<PositionModel> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<PositionModel>(
            positionService.savePosition(id, positionModel, username),
            HttpStatus.CREATED
        )
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @DeleteMapping(value = ["/{id}"], produces = ["application/json"])
    fun deletePositionById(
        @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String, @PathVariable id: Long?
    ): ResponseEntity<Void> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        positionService.delete(id, username)
        return ResponseEntity<Void>(HttpStatus.NO_CONTENT)
    }
}
