package de.ass73.ebt.dms.backend.services

import de.ass73.ebt.dms.backend.entities.ExpertiseEntity
import de.ass73.ebt.dms.backend.models.ExpertiseModel
import de.ass73.ebt.dms.backend.repository.ExpertiseRepo
import de.ass73.ebt.dms.backend.services.exceptions.BadServiceCallException
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class ExpertiseService(
    @Autowired
    private val expertiseRepo: ExpertiseRepo,
    @Autowired
    private val modelMapper: ModelMapper

) {
    fun getAllExpertises(username: String?): List<ExpertiseModel> {
        return expertiseRepo.findAll().stream()
            .map { expertise -> modelMapper.map(expertise, ExpertiseModel::class.java) }
            .collect(Collectors.toList())
    }

    fun getExpertiseById(id: Long, username: String?): ExpertiseModel {
        return expertiseRepo.findById(id).map { cert -> modelMapper.map(cert, ExpertiseModel::class.java) }
            .orElseThrow { BadServiceCallException("$id:no such id found") }
    }

    fun createExpertise(expertiseModel: ExpertiseModel?, username: String?): ExpertiseModel {
        val expertiseEntity: ExpertiseEntity = modelMapper.map(expertiseModel, ExpertiseEntity::class.java)
        return modelMapper.map(expertiseRepo.save(expertiseEntity), ExpertiseModel::class.java)
    }

    fun saveExpertise(id: Long, expertiseModel: ExpertiseModel?, username: String?): ExpertiseModel {
        return if (expertiseRepo.existsById(id)) {
            val expertiseEntity: ExpertiseEntity = modelMapper.map(expertiseModel, ExpertiseEntity::class.java)
            modelMapper.map(expertiseRepo.save(expertiseEntity), ExpertiseModel::class.java)
        } else {
            throw BadServiceCallException("$id:no such id found")
        }
    }

    fun deleteExpertise(id: Long, username: String?) {
        expertiseRepo.deleteById(id)
    }
}
