package de.ass73.ebt.dms.backend.repository

import de.ass73.ebt.dms.backend.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LoginRepository : JpaRepository<UserEntity, Long> {
    fun findByUsername2(username2: String): Optional<UserEntity>
}