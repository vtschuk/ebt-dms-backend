package de.ass73.ebt.dms.backend.services

import de.ass73.ebt.dms.backend.entities.EducationEntity
import de.ass73.ebt.efile.backend.models.EducationModel
import de.ass73.ebt.dms.backend.repository.EducationRepo
import de.ass73.ebt.dms.backend.services.exceptions.BadServiceCallException
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class EducationService(
    @Autowired
    private val educationRepo: EducationRepo,
    @Autowired
    private val modelMapper: ModelMapper
) {

    fun getAllEducations(username: String): List<EducationModel> {
        var entities = educationRepo.findAll()
        var models = entities
            .stream()
            .map { entity -> modelMapper.map(entity, EducationModel::class.java) }
            .collect(Collectors.toList())
        return models
    }

    fun getEducationById(id: Long, username: String?): EducationModel {
        return educationRepo.findById(id).map { education -> modelMapper.map(education, EducationModel::class.java) }
            .orElseThrow { BadServiceCallException("$id:no such id found") }
    }

    fun createEducation(educationModel: EducationModel?, username: String?): EducationModel {
        val educationEntity: EducationEntity = modelMapper.map(educationModel, EducationEntity::class.java)
        return modelMapper.map(educationRepo.save(educationEntity), EducationModel::class.java)
    }

    fun saveEducation(id: Long, educationModel: EducationModel?, username: String?): EducationModel {
        return if (educationRepo.existsById(id)) {
            val educationEntity: EducationEntity = modelMapper.map(educationModel, EducationEntity::class.java)
            modelMapper.map(educationRepo.save(educationEntity), EducationModel::class.java)
        } else {
            throw BadServiceCallException("$id:no such id found")
        }
    }

    fun delete(id: Long, username: String?) {
        educationRepo.deleteById(id)
    }
}
