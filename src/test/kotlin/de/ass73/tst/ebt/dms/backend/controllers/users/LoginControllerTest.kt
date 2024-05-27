package de.ass73.tst.ebt.dms.backend.controllers.users

import com.fasterxml.jackson.databind.ObjectMapper
import de.ass73.ebt.dms.backend.EbtDmsBackendApplication
import de.ass73.ebt.dms.backend.models.auth.LoginResponse
import de.ass73.ebt.dms.backend.models.auth.RegisterLoginRequest
import de.ass73.ebt.dms.backend.repository.JWTokenRepository
import de.ass73.ebt.dms.backend.services.users.LoginService
import de.ass73.ebt.dms.backend.services.users.LoginTools
import de.ass73.tst.ebt.dms.backend.tools.Generators
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@SpringBootTest(classes = [EbtDmsBackendApplication::class])
@AutoConfigureMockMvc
class LoginControllerTest {

    @MockBean
    private lateinit var loginService: LoginService

    @MockBean
    private lateinit var loginTools: LoginTools

    @MockBean
    private lateinit var jwTokenRepository: JWTokenRepository

    @Autowired
    private lateinit var mvc: MockMvc

    private val objectMapper: ObjectMapper = ObjectMapper()

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testCreateLogin() {
        //Given
        val registerLoginRequest = RegisterLoginRequest(
            Generators.generateString(8),
            Generators.generateString(8),
            "admin"
        )

        val loginResponse = LoginResponse(
            Generators.generateString(128)
        )
        Mockito.`when`(loginService.createLogin(registerLoginRequest)).thenReturn(loginResponse)

        //Then
        mvc.perform(
            MockMvcRequestBuilders.post("/api/login/create")
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(registerLoginRequest))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn()
    }

    @Disabled
    @Test
    fun testLogin() {
    }
}
