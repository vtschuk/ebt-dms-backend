package de.ass73.ebt.dms.backend.controllers.users

import de.ass73.ebt.dms.backend.models.auth.LoginRequest
import de.ass73.ebt.dms.backend.models.auth.LoginResponse
import de.ass73.ebt.dms.backend.models.auth.RegisterLoginRequest
import de.ass73.ebt.dms.backend.services.users.LoginService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/login")
class LoginController(
    @Autowired
    private val loginService: LoginService
) {

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping(value = ["/create"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun createLogin(@RequestBody registerLoginRequest: RegisterLoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity<LoginResponse>(loginService.createLogin(registerLoginRequest), HttpStatus.CREATED)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping(value = [""], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        return ResponseEntity<LoginResponse>(loginService.login(loginRequest), HttpStatus.OK)
    }
}
