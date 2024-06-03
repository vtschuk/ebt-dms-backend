package de.ass73.ebt.dms.backend.repository

import de.ass73.ebt.dms.backend.entities.FileEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Repository

@Repository
@EnableJpaRepositories("de.ass73.aktentool.repos")
interface DBRepoFileInterface : JpaRepository<FileEntity, Long>
