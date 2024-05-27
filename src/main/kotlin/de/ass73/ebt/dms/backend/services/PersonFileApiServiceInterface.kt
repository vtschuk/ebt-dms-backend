package de.ass73.ebt.dms.backend.services

import de.ass73.ebt.efile.backend.models.PersonModel

interface PersonFileApiServiceInterface {
    fun getAllPersons(username: String): List<PersonModel>
    fun getPersonById(id: Long, username: String): PersonModel
    fun create(personModel: PersonModel, username: String): PersonModel
    fun save(id: Long, personModel: PersonModel, username: String): PersonModel
    fun delete(id: Long, username: String): PersonModel
}
