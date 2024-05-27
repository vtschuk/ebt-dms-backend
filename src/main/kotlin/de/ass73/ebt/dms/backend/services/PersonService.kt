package de.ass73.ebt.efile.backend.services

import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PersonService {
    @Autowired
    private val modelMapper: ModelMapper? = null
    val allPersons: List<Any>
        get() = emptyList()
}
