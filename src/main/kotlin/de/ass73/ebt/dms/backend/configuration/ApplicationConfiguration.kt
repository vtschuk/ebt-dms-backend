package de.ass73.ebt.dms.backend.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import de.ass73.ebt.dms.backend.repository.LoginRepository
import org.modelmapper.ModelMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport


@Configuration
@EnableWebMvc
class ApplicationConfiguration(
    val repository: LoginRepository
) : WebMvcConfigurationSupport() {

    @Bean
    fun authenticationManager(
        userDetailsService: UserDetailsService,
        passwordEncoder: PasswordEncoder
    ): AuthenticationManager {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService)
        authenticationProvider.setPasswordEncoder(passwordEncoder)

        return ProviderManager(authenticationProvider)
    }


    @Bean
    fun userDetailsService(): UserDetailsService {
        return UserDetailsService { username: String ->
            repository.findByUsername2(username)
                .orElseThrow { UsernameNotFoundException("Login not found") }
        }
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationProvider(): AuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService())
        authProvider.setPasswordEncoder(passwordEncoder())
        return authProvider
    }

    @Bean
    fun loadModelMapper(): ModelMapper {
        val modelMapper = ModelMapper()
        return modelMapper
    }

    @Bean
    fun loadObjectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        return objectMapper
    }
}