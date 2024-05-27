package de.ass73.ebt.dms.backend.controllers.users

import de.ass73.ebt.efile.backend.models.UserModel
import de.ass73.ebt.dms.backend.services.LogoutService
import de.ass73.ebt.dms.backend.services.UserService
import de.ass73.ebt.dms.backend.services.users.LoginTools
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(
    @Autowired
    private val userService: UserService,

    @Autowired
    private val logoutService: LogoutService,

    @Autowired
    private val loginTools: LoginTools
) {

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping(value = ["/all"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getAllUsers(
        @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<List<UserModel>> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<List<UserModel>>(userService.getAllUsers(username), HttpStatus.OK)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun getUserById(
        @PathVariable id: String, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<UserModel> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<UserModel>(userService.getUserById(id, username), HttpStatus.OK)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PutMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun updateUser(
        @PathVariable id: Long, @RequestBody userModel: UserModel, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<UserModel> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        val tempModel = userService.updateUser(id, userModel, username)
        return ResponseEntity<UserModel>(tempModel, HttpStatus.OK)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @DeleteMapping(value = ["/{id}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun deleteUser(
        @PathVariable id: String, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<*> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        userService.deleteUser(id, username)
        return ResponseEntity<Any>(HttpStatus.NO_CONTENT)
    }


    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping(value = ["/logout"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun logoutUser(
        authentication: Authentication,
        request: HttpServletRequest,
        response: HttpServletResponse,

        ): ResponseEntity<*> {
        logoutService.logout(request, response, authentication)
        return ResponseEntity<Void>(HttpStatus.NO_CONTENT)
    }

}
