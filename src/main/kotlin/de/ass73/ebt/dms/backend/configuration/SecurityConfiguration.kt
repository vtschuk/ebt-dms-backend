package de.ass73.ebt.dms.backend.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.logout.LogoutHandler

@Configuration
@EnableWebSecurity
class SecurityConfiguration(
    val authenticationProvider: AuthenticationProvider,
    val jwtAuthenticationFilter: JWTAuthenticationFilter,
    val logoutHandler: LogoutHandler,

    ) {


    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        http
            .csrf { csrf -> csrf.disable() }
//            .authorizeHttpRequests {
//                req -> req.requestMatchers("/**").permitAll().anyRequest().authenticated()
//            }

            .authorizeHttpRequests { req ->
                req.requestMatchers("/api/login").permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .logout { logout ->
                logout.logoutUrl("/logout")
                    .addLogoutHandler(logoutHandler)
            }

        return http.build()
    }

}