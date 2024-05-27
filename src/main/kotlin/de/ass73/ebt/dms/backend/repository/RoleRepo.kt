package de.ass73.ebt.dms.backend.repository

import de.ass73.ebt.dms.backend.entities.RoleEntity
import org.springframework.data.jpa.repository.JpaRepository

interface RoleRepo : JpaRepository<RoleEntity?, Long?>
