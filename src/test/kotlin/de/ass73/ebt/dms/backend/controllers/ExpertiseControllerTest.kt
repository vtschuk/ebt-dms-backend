package de.ass73.ebt.tst.efile.backend.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.ass73.ebt.dms.backend.EbtDmsBackendApplication
import de.ass73.ebt.dms.backend.models.ExpertiseModel
import de.ass73.ebt.dms.backend.repository.JWTokenRepository
import de.ass73.ebt.dms.backend.services.ExpertiseService
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
class ExpertiseControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var expertiseService: ExpertiseService

    @MockBean
    private lateinit var loginTools: LoginTools

    @MockBean
    private lateinit var jwTokenRepository: JWTokenRepository

    var objectMapper: ObjectMapper = jacksonObjectMapper()
    private val username: String = Generators.generateString(8)

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testGetAllExpertises() {
        val expertiseModel = ExpertiseModel(
            Random().nextLong(),
            Generators.generateString(10)
        )
        Mockito.`when`(expertiseService.getAllExpertises(username)).thenReturn(listOf(expertiseModel))
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        val result: MvcResult = mvc.perform(
            MockMvcRequestBuilders.get("/admin/expertise/all")
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
        val returnValue: String = result.getResponse().getContentAsString()
        val expectedValue: String = objectMapper.writeValueAsString(listOf(expertiseModel))
        Assertions.assertEquals(returnValue, expectedValue)
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testGetExpertiseById() {
        val expertiseModel: ExpertiseModel = ExpertiseModel(
            Random().nextLong(),
            Generators.generateString(10),
        )
        Mockito.`when`(expertiseService.getExpertiseById(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString()))
            .thenReturn(expertiseModel)
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        val result: MvcResult = mvc.perform(
            MockMvcRequestBuilders.get("/admin/expertise/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
        )
            .andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
        val returnValue: String = result.getResponse().getContentAsString()
        val expectedValue: String = objectMapper.writeValueAsString(expertiseModel)
        Assertions.assertEquals(returnValue, expectedValue)
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testCreateNewExpertise() {
        //Given
        val expertiseModel = ExpertiseModel(
            Random().nextLong(),
            Generators.generateString(10),
        )

        //When
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn("user")
        Mockito.`when`(expertiseService.createExpertise(expertiseModel, username)).thenReturn(expertiseModel)

        //Then
        mvc.perform(
            MockMvcRequestBuilders.post("/admin/expertise").with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(expertiseModel))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated())
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testSaveExpertise() {
        val expertiseModel = ExpertiseModel(
            Random().nextLong(),
            Generators.generateString(10),
        )
        //When
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.any())).thenReturn("user")
        Mockito.`when`(
            expertiseService.saveExpertise(
                ArgumentMatchers.anyLong(),
                ArgumentMatchers.any(),
                ArgumentMatchers.anyString()
            )
        ).thenReturn(expertiseModel)

        //Then
        val result: MvcResult = mvc.perform(
            MockMvcRequestBuilders.put("/admin/expertise/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(expertiseModel))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn()

        // Mockito.verify(certificateModel, Mockito.atLeastOnce()).save(any(), any(), any());
        val returnedContent: String = result.getResponse().getContentAsString()
        val expectedContent: String = objectMapper.writeValueAsString(expertiseModel)
        Assertions.assertEquals(expectedContent, returnedContent)
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testDeleteExpertiseById() {
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        mvc.perform(
            MockMvcRequestBuilders.delete("/admin/expertise/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn()
    }
}
