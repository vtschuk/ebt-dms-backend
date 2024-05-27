package de.ass73.ebt.dms.backend.repository

import de.ass73.ebt.dms.backend.entities.EducationEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface EducationRepo : JpaRepository<EducationEntity, Long> {
    fun findByName(name: String): Optional<EducationEntity>
}
