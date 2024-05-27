package de.ass73.ebt.tst.efile.backend.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.ass73.ebt.dms.backend.EbtDmsBackendApplication
import de.ass73.ebt.dms.backend.models.PositionModel
import de.ass73.ebt.dms.backend.repository.JWTokenRepository
import de.ass73.ebt.dms.backend.services.PositionService
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
class PositionControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var positionService: PositionService

    @MockBean
    private lateinit var loginTools: LoginTools

    @MockBean
    private lateinit var jwTokenRepository: JWTokenRepository


    var objectMapper: ObjectMapper = jacksonObjectMapper()
    private val username: String = Generators.generateString(8)

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testGetAllPositions() {
        val positionModel = PositionModel(
            Random().nextLong(),
            Generators.generateString(10)

        )

        Mockito.`when`(positionService.getAllPositions(username)).thenReturn(listOf(positionModel))
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        val result: MvcResult = mvc.perform(
            MockMvcRequestBuilders.get("/admin/position/all")
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
        val returnValue: String = result.getResponse().getContentAsString()
        val expectedValue: String = objectMapper.writeValueAsString(listOf(positionModel))
        Assertions.assertEquals(returnValue, expectedValue)
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testGetPositionById() {
        val positionModel = PositionModel(
            Random().nextLong(),
            Generators.generateString(10)
        )
        Mockito.`when`(positionService.getPositionById(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString()))
            .thenReturn(positionModel)
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        val result: MvcResult = mvc.perform(
            MockMvcRequestBuilders.get("/admin/position/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
        )
            .andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
        val returnValue: String = result.getResponse().getContentAsString()
        val expectedValue: String = objectMapper.writeValueAsString(positionModel)
        Assertions.assertEquals(returnValue, expectedValue)
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testCreateNewPosition() {
        //Given
        val positionModel = PositionModel(
            Random().nextLong(),
            Generators.generateString(10)
        )

        //When
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn("user")
        Mockito.`when`(positionService.createPosition(positionModel, username)).thenReturn(positionModel)

        //Then
        mvc.perform(
            MockMvcRequestBuilders.post("/admin/position").with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(positionModel))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated())
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testSavePosition() {
        val positionModel = PositionModel(
            Random().nextLong(),
            Generators.generateString(10)
        )

        //When
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.any())).thenReturn("user")
        Mockito.`when`(
            positionService.savePosition(
                ArgumentMatchers.anyLong(),
                ArgumentMatchers.any(),
                ArgumentMatchers.anyString()
            )
        ).thenReturn(positionModel)

        //Then
        val result: MvcResult = mvc.perform(
            MockMvcRequestBuilders.put("/admin/position/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(positionModel))
        ).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn()

        // Mockito.verify(certificateModel, Mockito.atLeastOnce()).save(any(), any(), any());
        val returnedContent: String = result.getResponse().getContentAsString()
        val expectedContent: String = objectMapper.writeValueAsString(positionModel)
        Assertions.assertEquals(expectedContent, returnedContent)
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testDeletePositionById() {
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        mvc.perform(
            MockMvcRequestBuilders.delete("/admin/position/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn()
    }
}