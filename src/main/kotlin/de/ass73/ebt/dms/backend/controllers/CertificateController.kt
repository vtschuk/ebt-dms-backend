package de.ass73.ebt.dms.backend.controllers

import de.ass73.ebt.dms.backend.models.CertificateModel
import de.ass73.ebt.dms.backend.services.CertificateService
import de.ass73.ebt.dms.backend.services.users.LoginTools
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/admin/cert")
class CertificateController(
    @Autowired
    private val certificateService: CertificateService,

    @Autowired
    private val loginTools: LoginTools
) {

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping(value = ["/all"], produces = ["application/json"])
    fun getAllCertificates(
        @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<List<CertificateModel>> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<List<CertificateModel>>(certificateService.getAllCertifcates(username), HttpStatus.OK)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @GetMapping(value = ["/{id}"], produces = ["application/json"])
    fun getCertById(
        @PathVariable id: Long, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<CertificateModel> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<CertificateModel>(certificateService.getCertificateById(id, username), HttpStatus.OK)
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PostMapping(
        value = [""],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun createNewCert(
        @RequestBody certificateModel: CertificateModel, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<CertificateModel> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        return ResponseEntity<CertificateModel>(
            certificateService.createCertificate(certificateModel, username),
            HttpStatus.CREATED
        )
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @PutMapping(
        value = ["/{id}"],
        produces = [MediaType.APPLICATION_JSON_VALUE],
        consumes = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun saveCert(
        @PathVariable id: Long, @RequestBody certificateModel: CertificateModel, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String?
    ): ResponseEntity<CertificateModel> {
        val username: String = loginTools.extractUsername(autorization?.substring(7))
        return ResponseEntity<CertificateModel>(
            certificateService.saveCertificate(id, certificateModel, username),
            HttpStatus.CREATED
        )
    }

    @CrossOrigin(origins = ["http://localhost:4200"])
    @DeleteMapping(value = ["/{id}"], produces = ["application/json"])
    fun deleteCertById(
        @PathVariable id: Long, @RequestHeader(
            HttpHeaders.AUTHORIZATION
        ) autorization: String
    ): ResponseEntity<Void> {
        val username: String = loginTools.extractUsername(autorization.substring(7))
        certificateService.delete(id, username)
        return ResponseEntity<Void>(HttpStatus.NO_CONTENT)
    }

}
