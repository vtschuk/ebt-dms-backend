package de.ass73.ebt.dms.backend.controllers

import de.ass73.ebt.dms.backend.services.EfileService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class EFileController(@Autowired private val efileService: EfileService) {

    @CrossOrigin
    @GetMapping("/test/{id}")
    fun getTask(@PathVariable id: Long): ResponseEntity<String> {
        return ResponseEntity("Hallo Welt!$id", HttpStatus.OK)
    }

    @GetMapping("/")
    fun helloWorld(): ResponseEntity<String> {
        return ResponseEntity("Hello World!", HttpStatus.OK)
    }
}