package de.ass73.ebt.dms.backend.repository

import de.ass73.ebt.dms.backend.entities.CertificateEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CertRepo : JpaRepository<CertificateEntity, Long> {
    fun findByName(name: String): Optional<CertificateEntity>
}
