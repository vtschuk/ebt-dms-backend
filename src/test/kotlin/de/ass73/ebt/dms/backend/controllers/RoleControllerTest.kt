package de.ass73.ebt.tst.efile.backend.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import de.ass73.ebt.dms.backend.EbtDmsBackendApplication
import de.ass73.ebt.dms.backend.repository.JWTokenRepository
import de.ass73.ebt.dms.backend.services.RoleService
import de.ass73.ebt.dms.backend.services.users.LoginTools
import de.ass73.ebt.dms.backend.tools.Generators
import de.ass73.ebt.efile.backend.models.RoleModel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpHeaders
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@SpringBootTest(classes = arrayOf(EbtDmsBackendApplication::class))
@AutoConfigureMockMvc
class RoleControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var roleService: RoleService

    @MockBean
    private lateinit var loginTools: LoginTools

    @MockBean
    private lateinit var jwTokenRepository: JWTokenRepository

    var objectMapper: ObjectMapper = ObjectMapper()
    private val username: String = Generators.generateString(8)

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testGetAllRoles() {
        val roleModel = RoleModel(
            Random().nextLong(),
            Generators.generateString(10)
        )

        Mockito.`when`(roleService.getAllRoles(username)).thenReturn(listOf(roleModel))
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        val result: MvcResult = mvc.perform(
            MockMvcRequestBuilders.get("/admin/role/all")
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
        val returnValue: String = result.getResponse().getContentAsString()
        val expectedValue: String = objectMapper.writeValueAsString(listOf(roleModel))
        Assertions.assertEquals(returnValue, expectedValue)
    }

    @WithMockUser
    @Test
    fun testChangeRole() {
    }
}
