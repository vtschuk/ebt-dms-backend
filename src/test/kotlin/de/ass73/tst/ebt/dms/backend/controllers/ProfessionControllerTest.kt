package de.ass73.tst.ebt.dms.backend.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import de.ass73.ebt.dms.backend.EbtDmsBackendApplication
import de.ass73.ebt.dms.backend.models.ProfessionModel
import de.ass73.ebt.dms.backend.repository.JWTokenRepository
import de.ass73.ebt.dms.backend.services.ProfessionService
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
class ProfessionControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var professionService: ProfessionService

    @MockBean
    private lateinit var loginTools: LoginTools

    @MockBean
    private lateinit var jwTokenRepository: JWTokenRepository

    var objectMapper: ObjectMapper = ObjectMapper()
    private val username: String = Generators.generateString(8)

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testGetAllProfessions() {
        val professionModel = ProfessionModel(
            Random().nextLong(),
            Generators.generateString(10)
        )
        Mockito.`when`(professionService.getAllProfessions(username)).thenReturn(listOf(professionModel))
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        val result: MvcResult = mvc.perform(
            MockMvcRequestBuilders.get("/admin/profession/all")
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
        val returnValue: String = result.getResponse().getContentAsString()
        val expectedValue: String = objectMapper.writeValueAsString(listOf(professionModel))
        Assertions.assertEquals(returnValue, expectedValue)
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testGetProfessionById() {
        //Given
        val professionModel = ProfessionModel(
            Random().nextLong(),
            Generators.generateString(10)
        )

        //When
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        Mockito.`when`(professionService.getProfessionById(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString()))
            .thenReturn(professionModel)

        //Then
        val result: MvcResult = mvc.perform(
            MockMvcRequestBuilders.get("/admin/profession/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
        )
            .andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
        val returnValue: String = result.getResponse().getContentAsString()
        val expectedValue: String = objectMapper.writeValueAsString(professionModel)
        Assertions.assertEquals(returnValue, expectedValue)
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testCreateNewProfession() {
        //Given
        val professionModel = ProfessionModel(
            Random().nextLong(),
            Generators.generateString(10)
        )
        //When
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn("user")
        Mockito.`when`(professionService.createProfession(professionModel, username)).thenReturn(professionModel)

        //Then
        mvc.perform(
            MockMvcRequestBuilders.post("/admin/profession").with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(professionModel))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated())
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testSaveProfession() {
        val professionModel = ProfessionModel(
            Random().nextLong(),
            Generators.generateString(10)
        )

        //When
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.any())).thenReturn("user")
        Mockito.`when`(
            professionService.save(
                ArgumentMatchers.anyLong(),
                ArgumentMatchers.any(),
                ArgumentMatchers.anyString()
            )
        ).thenReturn(professionModel)

        //Then
        val result: MvcResult = mvc.perform(
            MockMvcRequestBuilders.put("/admin/profession/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(professionModel))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn()

        // Mockito.verify(certificateModel, Mockito.atLeastOnce()).save(any(), any(), any());
        val returnedContent: String = result.getResponse().getContentAsString()
        val expectedContent: String = objectMapper.writeValueAsString(professionModel)
        Assertions.assertEquals(expectedContent, returnedContent)
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testDeleteProfessionById() {
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        mvc.perform(
            MockMvcRequestBuilders.delete("/admin/profession/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn()
    }
}
