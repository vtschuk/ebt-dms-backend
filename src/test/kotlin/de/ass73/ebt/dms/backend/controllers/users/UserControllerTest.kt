package de.ass73.ebt.dms.backend.controllers.users

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.ass73.ebt.dms.backend.EbtDmsBackendApplication
import de.ass73.ebt.dms.backend.models.UserModel
import de.ass73.ebt.dms.backend.repository.JWTokenRepository
import de.ass73.ebt.dms.backend.services.LogoutService
import de.ass73.ebt.dms.backend.services.UserService
import de.ass73.ebt.dms.backend.services.users.LoginTools
import de.ass73.ebt.dms.backend.tools.Generators
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
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
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@SpringBootTest(classes = arrayOf(EbtDmsBackendApplication::class))
@AutoConfigureMockMvc
class UserControllerTest {

    @MockBean
    private lateinit var userService: UserService

    @MockBean
    private lateinit var loginTools: LoginTools

    @MockBean
    private lateinit var tokenRepository: JWTokenRepository

    @MockBean
    private lateinit var logoutService: LogoutService

    @Autowired
    private lateinit var mockMvc: MockMvc

    var objectMapper: ObjectMapper = jacksonObjectMapper()
    private val username: String = Generators.generateString(8)

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testGetAllUsers() {
        //Given
        val userModel = UserModel(
            Random().nextLong(),
            Generators.generateString(8),
            Generators.generateString(8),
            "admin",
        )
        //When
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        Mockito.`when`(userService.getAllUsers(ArgumentMatchers.anyString())).thenReturn(mutableListOf(userModel))

        //Then
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/user/all").with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testGetUserById() {
        //Given
        val userModel = UserModel(
            Random().nextLong(),
            Generators.generateString(8),
            Generators.generateString(8),
            "admin"
        );

        //When
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        Mockito.`when`(userService.getUserById(ArgumentMatchers.anyString(), ArgumentMatchers.anyString()))
            .thenReturn(userModel)

        //Then
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/user/1").with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testUpdateUser() {
        //Given
        val userModel = UserModel(
            Random().nextLong(),
            Generators.generateString(8),
            Generators.generateString(8),
            "admin"
        )

        //When
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        Mockito.`when`(
            userService.updateUser(
                1L,
                userModel,
                username
            )
        ).thenReturn(userModel)

        //Then
        val result: MvcResult = mockMvc.perform(
            MockMvcRequestBuilders.put("/api/user/1")

                .accept(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userModel))

        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
        val returnedVal: String = result.getResponse().getContentAsString()
        val expectedVal: String = objectMapper.writeValueAsString(userModel)
        Assertions.assertEquals(expectedVal, returnedVal)
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testDeleteUser() {
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/user/1")
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
        ).andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn()
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testLogoutUser() {
        //Mockito.when(logoutService.logout(any(), any(), any())).thenReturn()
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/user/logout")
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
        ).andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn()
    }
}
