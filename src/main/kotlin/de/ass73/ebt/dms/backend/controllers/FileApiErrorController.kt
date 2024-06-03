package de.ass73.ebt.dms.backend.controllers

import org.springframework.boot.web.servlet.error.ErrorController
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FileApiErrorController : ErrorController {
    @RequestMapping("/error")
    @CrossOrigin(origins = ["http://localhost:4200"])
    fun error(): String {
        return "Der Service Aufruf ist falsch"
    }

    val errorPath: String
        get() = "/error"
}