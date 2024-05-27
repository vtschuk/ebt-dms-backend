package de.ass73.ebt.dms.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EbtDmsBackendApplication

fun main(args: Array<String>) {
    runApplication<EbtDmsBackendApplication>(*args)
}
