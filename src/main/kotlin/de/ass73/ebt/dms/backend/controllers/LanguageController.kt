package de.ass73.ebt.dms.backend.controllers

import de.ass73.ebt.efile.backend.models.LanguageModel
import de.ass73.ebt.dms.backend.services.LanguageService
import de.ass73.ebt.dms.backend.services.users.LoginTools
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/language")
class LanguageController(
    @Autowired
    private val languageService: LanguageService,

    @Autowired
    private val loginTools: LoginTools

) {


    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping(value = ["/all"], produces = ["application/json"])
    fun getAllLanguages(
        @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<List<LanguageModel>> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<List<LanguageModel>>(languageService.getAllLanguages(username), HttpStatus.OK)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping(value = [""], produces = ["application/json"])
    fun change(
        @RequestBody languageModel: LanguageModel?, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<LanguageModel> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<LanguageModel>(
            languageModel?.let { languageService.change(it, username) },
            HttpStatus.CREATED
        )
    }
}
