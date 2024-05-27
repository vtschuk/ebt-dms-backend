package de.ass73.ebt.dms.backend.repository

import de.ass73.ebt.dms.backend.entities.ProfessionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProfessionRepo : JpaRepository<ProfessionEntity, Long> {
    fun findByName(name: String): Optional<ProfessionEntity>
}
