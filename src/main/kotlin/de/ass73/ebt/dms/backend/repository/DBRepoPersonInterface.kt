package de.ass73.ebt.dms.backend.repository

import de.ass73.ebt.dms.backend.entities.PersonEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Repository

@Repository
@EnableJpaRepositories("de.ass73.aktentool.repos")
interface DBRepoPersonInterface : JpaRepository<PersonEntity, Long>
