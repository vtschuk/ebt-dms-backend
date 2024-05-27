package de.ass73.ebt.dms.backend.services

import de.ass73.ebt.dms.backend.entities.PositionEntity
import de.ass73.ebt.dms.backend.entities.UserEntity
import de.ass73.ebt.efile.backend.models.PositionModel
import de.ass73.ebt.dms.backend.repository.LoginRepository
import de.ass73.ebt.dms.backend.repository.PositionRepo
import de.ass73.ebt.dms.backend.services.exceptions.BadServiceCallException
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.stream.Collectors

@Service
class PositionService(
    @Autowired
    private val positionRepo: PositionRepo,

    @Autowired
    private val loginRepository: LoginRepository,

    @Autowired
    private val modelMapper: ModelMapper,

    ) {

    /* used by group: read, edit, admin */
    fun getAllPositions(username: String): List<PositionModel> {
        val userEntity: UserEntity? = loginRepository.findByUsername2(username).orElseThrow()
        return if (userEntity?.role == "admin") {
            positionRepo.findAll().stream()
                .map { cert -> modelMapper.map(cert, PositionModel::class.java) }
                .collect(Collectors.toList())
        } else Collections.emptyList()
    }


    /* Group: read, edit, admin */
    fun getPositionById(id: Long, username: String?): PositionModel {
        return positionRepo.findById(id).map { position -> modelMapper.map(position, PositionModel::class.java) }
            .orElseThrow { BadServiceCallException("$id:no such id found") }
    }

    /* Group: admin */
    fun createPosition(positionModel: PositionModel, username: String): PositionModel {
        val positionEntity: PositionEntity = modelMapper.map(positionModel, PositionEntity::class.java)
        println("Mapped to Entity: ${positionEntity.id}, ${positionEntity.name}")
        return modelMapper.map(positionRepo.save(positionEntity), PositionModel::class.java)
    }

    /* Group: admin */
    fun savePosition(id: Long, positionModel: PositionModel?, username: String?): PositionModel {
        return if (positionRepo.existsById(id)) {
            val positionEntity: PositionEntity = modelMapper.map(positionModel, PositionEntity::class.java)
            modelMapper.map(positionRepo.save(positionEntity), PositionModel::class.java)
        } else {
            throw BadServiceCallException("$id:no such id found")
        }
    }

    /* Group: admin */
    fun delete(id: Long?, username: String?) {
        if (id != null) {
            positionRepo.deleteById(id)
        }
    }
}
