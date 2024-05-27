package de.ass73.ebt.dms.backend.repository

import de.ass73.ebt.dms.backend.entities.JWToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface JWTokenRepository : JpaRepository<JWToken, Long> {


    @Query(
        value = """
            select t from JWToken t inner join UserEntity u
            on t.userEntity.id = u.id
            where u.id = :id and (t.expired = false or t.revoked = false)
            """
    )
    fun findAllValidTokenByUser(id: Long): List<JWToken>
    fun findByToken(token: String?): Optional<JWToken>
}