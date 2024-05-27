package de.ass73.ebt.dms.backend.repository

import de.ass73.ebt.dms.backend.entities.ExpertiseEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ExpertiseRepo : JpaRepository<ExpertiseEntity, Long> {
    fun findByName(name: String): Optional<ExpertiseEntity>
}
