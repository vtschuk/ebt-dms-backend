package de.ass73.ebt.dms.backend.repository

import de.ass73.ebt.dms.backend.entities.PositionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface PositionRepo : JpaRepository<PositionEntity, Long> {
    fun findByName(name: String): Optional<PositionEntity>?
}
