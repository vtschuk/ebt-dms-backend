package de.ass73.ebt.dms.backend.repository

import de.ass73.ebt.dms.backend.entities.Efile
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EfileRepository : JpaRepository<Efile, Long>