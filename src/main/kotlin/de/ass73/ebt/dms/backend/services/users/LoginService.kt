package de.ass73.ebt.dms.backend.services.users

import de.ass73.ebt.dms.backend.entities.JWToken
import de.ass73.ebt.dms.backend.entities.TokenType
import de.ass73.ebt.dms.backend.entities.UserEntity
import de.ass73.ebt.dms.backend.models.auth.ChangeLoginRequest
import de.ass73.ebt.dms.backend.models.auth.LoginRequest
import de.ass73.ebt.dms.backend.models.auth.LoginResponse
import de.ass73.ebt.dms.backend.models.auth.RegisterLoginRequest
import de.ass73.ebt.dms.backend.repository.JWTokenRepository
import de.ass73.ebt.dms.backend.repository.LoginRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class LoginService(
    @Autowired
    private val loginRepository: LoginRepository,

    @Autowired
    private val loginTools: LoginTools,

    @Autowired
    private val tokenRepository: JWTokenRepository,

    @Autowired
    private val authenticationManager: AuthenticationManager,

    @Autowired
    private val passwordEncoder: PasswordEncoder
) {

    fun createLogin(registerLoginRequest: RegisterLoginRequest): LoginResponse {
        val login = UserEntity(
            0,
            registerLoginRequest.firstName,
            registerLoginRequest.lastName,
            registerLoginRequest.email,
            registerLoginRequest.username,
            passwordEncoder.encode(registerLoginRequest.password),
            registerLoginRequest.role
        )
        val savedLogin = loginRepository.save(login)
        val token = loginTools.generateToken(login)
        saveLoginToken(savedLogin, token)
        return LoginResponse(savedLogin.id, token)
    }

    fun login(loginRequest: LoginRequest): LoginResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                loginRequest.username,
                loginRequest.password
            )
        )
        val login = loginRepository.findByUsername2(loginRequest.username).orElseThrow()
        val token = loginTools.generateToken(login)
        revokeAllLoginTokens(login)
        saveLoginToken(login, token)
        return LoginResponse(login.id, token)
    }

    private fun saveLoginToken(userEntity: UserEntity, token: String) {
        val token2 = JWToken(0L, token, TokenType.BEARER, false, false, userEntity)
        tokenRepository.save(token2)
    }

    private fun revokeAllLoginTokens(userEntity: UserEntity) {
        val validLoginTokens: List<JWToken?> = tokenRepository.findAllValidTokenByUser(userEntity.id)

        validLoginTokens.forEach { token ->
            if (token != null) {
                token.expired = true
            }
            if (token != null) {
                token.revoked = true
            }
        }
        tokenRepository.saveAll(validLoginTokens)
    }

    fun changeLogin(changeLoginRequest: ChangeLoginRequest, username: String): LoginResponse {
        if(changeLoginRequest.newpassword == changeLoginRequest.newpassword2) {
            val userEntity = loginRepository.findByUsername2(username).orElseThrow()
             userEntity.password2 = passwordEncoder.encode(changeLoginRequest.newpassword)
            val savedUserEntity = loginRepository.save(userEntity)
            val token= loginTools.generateToken(savedUserEntity)
            saveLoginToken(savedUserEntity, token)
            return LoginResponse(savedUserEntity.id, token)
        } else {
            throw  Exception("")
        }
    }
}
