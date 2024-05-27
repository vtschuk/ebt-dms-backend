package de.ass73.ebt.dms.backend.services

import de.ass73.ebt.dms.backend.entities.JWToken
import de.ass73.ebt.dms.backend.repository.JWTokenRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.logout.LogoutHandler
import org.springframework.stereotype.Service

@Service
class LogoutService : LogoutHandler {
    @Autowired
    private val tokenRepository: JWTokenRepository? = null
    override fun logout(request: HttpServletRequest, response: HttpServletResponse, authentication: Authentication) {
        val authHeader: String = request.getHeader("Authorization")
        val jwt: String
        if (!authHeader.startsWith("Bearer ")) {
            return
        }
        jwt = authHeader.substring(7)
        val storedToken: JWToken? = tokenRepository?.findByToken(jwt)
            ?.orElse(null)
        if (storedToken != null) {
            storedToken.expired = true
            storedToken.revoked = true
            tokenRepository?.save(storedToken)
            SecurityContextHolder.clearContext()
        }
    }
}