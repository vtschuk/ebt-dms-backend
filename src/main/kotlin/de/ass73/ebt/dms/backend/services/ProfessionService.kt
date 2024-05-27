package de.ass73.ebt.dms.backend.services

import de.ass73.ebt.dms.backend.entities.ProfessionEntity
import de.ass73.ebt.efile.backend.models.ProfessionModel
import de.ass73.ebt.dms.backend.repository.ProfessionRepo
import de.ass73.ebt.dms.backend.services.exceptions.BadServiceCallException
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class ProfessionService(
    @Autowired
    private val professionRepo: ProfessionRepo,

    @Autowired
    private val modelMapper: ModelMapper

) {

    /* used by group: read, edit, admin */
    fun getAllProfessions(username: String): List<ProfessionModel> {
        return professionRepo.findAll().stream()
            .map { cert -> modelMapper.map(cert, ProfessionModel::class.java) }
            .collect(Collectors.toList())
    }

    /* used by group: read, edit, admin */
    fun getProfessionById(id: Long, username: String?): ProfessionModel {
        return professionRepo.findById(id).map { position ->
            modelMapper.map(position, ProfessionModel::class.java)
        }.orElseThrow { BadServiceCallException("$id:no such id found") }
    }

    /* used by group:  admin */
    fun createProfession(professionModel: ProfessionModel, username: String): ProfessionModel {
        val professionEntity: ProfessionEntity =
            modelMapper.map(professionModel, ProfessionEntity::class.java)
        return modelMapper.map(
            professionRepo.save(professionEntity),
            ProfessionModel::class.java
        )
    }

    /* used by group:  admin */
    fun save(id: Long, professionModel: ProfessionModel?, username: String?): ProfessionModel {
        return if (professionRepo.existsById(id) == true) {
            val professionEntity: ProfessionEntity =
                modelMapper.map(professionModel, ProfessionEntity::class.java)
            modelMapper.map(professionRepo.save(professionEntity), ProfessionModel::class.java)
        } else {
            throw BadServiceCallException("$id:no such id found")
        }
    }

    /* used by group: admin */
    fun delete(id: Long, username: String) {
        professionRepo.deleteById(id)
    }
}
