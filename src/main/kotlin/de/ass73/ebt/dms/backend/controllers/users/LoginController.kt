package de.ass73.ebt.dms.backend.controllers.users

import de.ass73.ebt.dms.backend.models.auth.ChangeLoginRequest
import de.ass73.ebt.dms.backend.models.auth.LoginRequest
import de.ass73.ebt.dms.backend.models.auth.LoginResponse
import de.ass73.ebt.dms.backend.models.auth.RegisterLoginRequest
import de.ass73.ebt.dms.backend.services.users.LoginService
import de.ass73.ebt.dms.backend.services.users.LoginTools
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/login")
class LoginController(
    @Autowired
    private val loginService: LoginService,

    @Autowired
    private val  loginTools: LoginTools
) {

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping(value = ["/create"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createLogin(@RequestBody registerLoginRequest: RegisterLoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity<LoginResponse>(loginService.createLogin(registerLoginRequest), HttpStatus.CREATED)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping(value = ["/change"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun changeLogin(@RequestBody changeLoginRequest: ChangeLoginRequest, @RequestHeader(
        HttpHeaders.AUTHORIZATION
    ) autorization: String): ResponseEntity<LoginResponse> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<LoginResponse>(loginService.changeLogin(changeLoginRequest, username), HttpStatus.OK)
    }


    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping(value = [""], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity<LoginResponse>(loginService.login(loginRequest), HttpStatus.OK)
    }
}
