package de.ass73.tst.ebt.dms.backend.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import de.ass73.ebt.dms.backend.EbtDmsBackendApplication
import de.ass73.ebt.dms.backend.models.LanguageModel
import de.ass73.ebt.dms.backend.repository.JWTokenRepository
import de.ass73.ebt.dms.backend.services.LanguageService
import de.ass73.ebt.dms.backend.services.users.LoginTools
import de.ass73.tst.ebt.dms.backend.tools.Generators
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
class LanguageControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var languageService: LanguageService

    @MockBean
    private lateinit var loginTools: LoginTools

    @MockBean
    private lateinit var jwTokenRepository: JWTokenRepository

    var objectMapper: ObjectMapper = ObjectMapper()
    private val username: String = Generators.generateString(8)

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testGetAllLanguages() {
        val languageModel = LanguageModel(
            Random().nextLong(),
            Generators.generateString(10),
            true
        )
        Mockito.`when`(languageService.getAllLanguages(username)).thenReturn(listOf(languageModel))
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        val result: MvcResult = mvc.perform(
            MockMvcRequestBuilders.get("/admin/language/all")
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
        val returnValue: String = result.getResponse().getContentAsString()
        val expectedValue: String = objectMapper.writeValueAsString(listOf(languageModel))
        Assertions.assertEquals(returnValue, expectedValue)
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testChangeLanguage() {
        val languageModel = LanguageModel(
            Random().nextLong(),
            Generators.generateString(10),
            true
        )

        mvc.perform(
            MockMvcRequestBuilders.post("/admin/language")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(languageModel))
        ).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn()
    }
}
