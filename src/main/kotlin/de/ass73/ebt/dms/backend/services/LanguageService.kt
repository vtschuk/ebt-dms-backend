package de.ass73.ebt.dms.backend.services

import de.ass73.ebt.dms.backend.entities.LanguageEntity
import de.ass73.ebt.dms.backend.models.LanguageModel
import de.ass73.ebt.dms.backend.repository.LanguageRepo
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Consumer
import java.util.stream.Collectors

@Service
class LanguageService(
    @Autowired
    private val languageRepo: LanguageRepo,

    @Autowired
    private val modelMapper: ModelMapper

) {
    fun getAllLanguages(username: String?): List<LanguageModel> {
        return languageRepo.findAll().stream()
            .map { entity -> modelMapper.map(entity, LanguageModel::class.java) }
            ?.collect(Collectors.toList()) ?: Collections.emptyList()
    }

    fun change(languageModel: LanguageModel, username: String?): LanguageModel {
        val entities: List<LanguageEntity?> = languageRepo.getByAktiv(true)
        entities.forEach(Consumer { entity: LanguageEntity? ->
            if (entity != null) {
                entity.aktiv = true
                languageRepo.save(entity)
            };
        })
        val entity: LanguageEntity = modelMapper.map(languageModel, LanguageEntity::class.java)
        return modelMapper.map(languageRepo.save(entity), LanguageModel::class.java)
    }
}
