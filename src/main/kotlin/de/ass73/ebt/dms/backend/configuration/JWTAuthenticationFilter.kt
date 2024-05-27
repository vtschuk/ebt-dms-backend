package de.ass73.ebt.dms.backend.configuration

import de.ass73.ebt.dms.backend.repository.JWTokenRepository
import de.ass73.ebt.dms.backend.services.users.LoginTools
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.lang.NonNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

@Component
class JWTAuthenticationFilter(
    private val jwtService: LoginTools,
    private val userDetailsService: UserDetailsService,
    private val tokenRepository: JWTokenRepository,
) : OncePerRequestFilter() {


    @Throws(ServletException::class, IOException::class)
    override fun doFilterInternal(
        @NonNull request: HttpServletRequest,
        @NonNull response: HttpServletResponse,
        @NonNull filterChain: FilterChain
    ) {
        val authHeader: String? = request.getHeader("Authorization")
        val jwt: String
        val username: String
        if (authHeader != null) {
            if (!authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response)
                return
            }
            jwt = authHeader.substring(7)
            username = jwtService.extractUsername(jwt)
            if (SecurityContextHolder.getContext().getAuthentication() == null) {
                val userDetails: UserDetails = userDetailsService.loadUserByUsername(username) ?: throw Exception()
                val isTokenValid: Boolean = tokenRepository.findByToken(jwt)
                    .map { t -> !t?.expired!! && !t.revoked }
                    ?.orElse(false) ?: false
                if (jwtService.isTokenValid(jwt, userDetails) == true && isTokenValid) {
                    val authToken = UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                    )
                    authToken.setDetails(
                        WebAuthenticationDetailsSource().buildDetails(request)
                    )
                    SecurityContextHolder.getContext().setAuthentication(authToken)
                }
            }
        }

        filterChain.doFilter(request, response)
    }
}