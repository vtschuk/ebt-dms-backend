package de.ass73.ebt.dms.backend.repository

import de.ass73.ebt.dms.backend.entities.LanguageEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LanguageRepo : JpaRepository<LanguageEntity?, Long?> {
    fun getByAktiv(b: Boolean): List<LanguageEntity?>
}
