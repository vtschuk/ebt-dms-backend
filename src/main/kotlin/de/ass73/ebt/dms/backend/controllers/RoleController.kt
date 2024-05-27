package de.ass73.ebt.dms.backend.controllers


import de.ass73.ebt.efile.backend.models.ChangeRoleModel
import de.ass73.ebt.efile.backend.models.RoleModel
import de.ass73.ebt.dms.backend.services.RoleService
import de.ass73.ebt.dms.backend.services.users.LoginTools
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/role")
class RoleController {
    @Autowired
    private val roleService: RoleService? = null

    @Autowired
    private val loginTools: LoginTools? = null

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping(value = ["/all"], produces = ["application/json"])
    fun getAllRoles(
        @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<List<RoleModel>> {
        val username: String = loginTools?.extractUsername(autorization.substring(7)) ?: ""
        return ResponseEntity<List<RoleModel>>(roleService?.getAllRoles(username), HttpStatus.OK)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PutMapping
    fun changeRole(
        @RequestHeader(HttpHeaders.AUTHORIZATION) autorization: String,
        @RequestBody changeRoleModel: ChangeRoleModel?
    ): ResponseEntity<RoleModel> {
        val login: String = loginTools?.extractUsername(autorization.substring(7)) ?: ""
        return ResponseEntity<RoleModel>(roleService?.changeRole(login, changeRoleModel), HttpStatus.OK)
    }
}
