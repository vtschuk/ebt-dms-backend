package de.ass73.ebt.dms.backend.services

import de.ass73.ebt.dms.backend.entities.PersonEntity
import de.ass73.ebt.dms.backend.models.PersonModel
import de.ass73.ebt.dms.backend.repository.DBRepoPersonInterface
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class PersonFileApiService(
    @Autowired
    private val dbRepoPersonInterface: DBRepoPersonInterface,
    @Autowired
    private val mapper: ModelMapper
) : PersonFileApiServiceInterface {

    override fun getAllPersons(username: String): List<PersonModel> {
        return dbRepoPersonInterface.findAll().stream()
            .map { personEntity -> mapper.map(personEntity, PersonModel::class.java) }
            .collect(Collectors.toList())
    }

    override fun getPersonById(id: Long, username: String): PersonModel {
        val personEntity: PersonEntity = dbRepoPersonInterface.findById(id).orElseThrow()
        //  .orElseThrow{ ex -> NoContentException("Kann keine Akte mit der ID $id finden") }

        return mapper.map(personEntity, PersonModel::class.java)
    }

    override fun create(personModel: PersonModel, username: String): PersonModel {
        var personEntity: PersonEntity = mapper.map(personModel, PersonEntity::class.java)
        personEntity = dbRepoPersonInterface.save(personEntity)
        return mapper.map(personEntity, PersonModel::class.java)
    }

    //todo: make it immutable
    override fun save(id: Long, personModel: PersonModel, username: String): PersonModel {
        val personEntity: PersonEntity = mapper.map(personModel, PersonEntity::class.java)
        var personEntityDB: PersonEntity = dbRepoPersonInterface.findById(id).orElseThrow()
        //.orElseThrow { NoContentException("Kann keinen Eintrag mit der ID: $id finden") }
        personEntityDB.vorname = personEntity.vorname
        personEntityDB.name = personEntity.name
        personEntityDB.email = personEntity.email
        personEntityDB.birthsday = personEntity.birthsday
        personEntityDB.cellphone = personEntity.cellphone
        personEntityDB.address = personEntity.address
        personEntityDB = dbRepoPersonInterface.save(personEntityDB)
        return mapper.map(personEntityDB, PersonModel::class.java)
    }

    override fun delete(id: Long, username: String): PersonModel {
        println("Delete Person with id $id")
        val personEntity: PersonEntity = dbRepoPersonInterface.getReferenceById(id)
        dbRepoPersonInterface.deleteById(id)
        return mapper.map(personEntity, PersonModel::class.java)
    }

}

