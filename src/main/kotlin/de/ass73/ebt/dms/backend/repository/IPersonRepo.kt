package de.ass73.ebt.dms.backend.repository

import de.ass73.ebt.dms.backend.entities.PersonEntity
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
@EntityScan(value = ["entities"])
interface IPersonRepo : JpaRepository<PersonEntity, Long>
