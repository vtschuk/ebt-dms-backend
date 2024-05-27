package de.ass73.tst.ebt.dms.backend.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import de.ass73.ebt.dms.backend.EbtDmsBackendApplication
import de.ass73.ebt.dms.backend.models.CertificateModel
import de.ass73.ebt.dms.backend.repository.JWTokenRepository
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

@SpringBootTest(classes = [EbtDmsBackendApplication::class])
@AutoConfigureMockMvc
class CertificateControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var certificateService: de.ass73.ebt.dms.backend.services.CertificateService

    @MockBean
    private lateinit var loginTools: LoginTools

    @MockBean
    private lateinit var jwTokenRepository: JWTokenRepository

    var objectMapper: ObjectMapper = jacksonObjectMapper()
    private val username: String = Generators.generateString(8)

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testGetAllCertificates() {
        val certificateModel = CertificateModel(Random().nextLong(), Generators.generateString(10))
        Mockito.`when`(certificateService.getAllCertifcates(ArgumentMatchers.anyString()))
            .thenReturn(listOf(certificateModel))
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)

        val result: MvcResult = mvc.perform(
            MockMvcRequestBuilders.get("/admin/cert/all")
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn()


        val returnValue: String = result.getResponse().getContentAsString()
        val expectedValue: String = objectMapper.writeValueAsString(listOf(certificateModel))
        Assertions.assertEquals(returnValue, expectedValue)
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testGetCertById() {
        //Given
        val certificateModel = CertificateModel(Random().nextLong(), Generators.generateString(10))

        //When
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        Mockito.`when`(certificateService.getCertificateById(ArgumentMatchers.anyLong(), ArgumentMatchers.anyString()))
            .thenReturn(certificateModel)

        //Then
        val result: MvcResult = mvc.perform(
            MockMvcRequestBuilders.get("/admin/cert/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
        )
            .andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
        val returnValue: String = result.getResponse().getContentAsString()
        val expectedValue: String = objectMapper.writeValueAsString(certificateModel)
        Assertions.assertEquals(returnValue, expectedValue)
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testCreateNewCert() {
        //Given
        val certificateModel = CertificateModel(Random().nextLong(), Generators.generateString(10))


        //When
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        Mockito.`when`(certificateService.createCertificate(certificateModel, username)).thenReturn(certificateModel)

        //Then
        mvc.perform(
            MockMvcRequestBuilders.post("/admin/cert").with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(certificateModel))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn()
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testSaveCert() {
        val certificateModel = CertificateModel(Random().nextLong(), Generators.generateString(10))

        //When
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.any())).thenReturn(username)
        Mockito.`when`(
            certificateService.saveCertificate(
                1L,
                certificateModel,
                username
            )
        ).thenReturn(certificateModel)

        //Then
        val result: MvcResult = mvc.perform(
            MockMvcRequestBuilders.put("/admin/cert/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(certificateModel))
        ).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn()

        // Mockito.verify(certificateModel, Mockito.atLeastOnce()).save(any(), any(), any());
        val returnedContent: String = result.getResponse().getContentAsString()
        val expectedContent: String = objectMapper.writeValueAsString(certificateModel)
        Assertions.assertEquals(expectedContent, returnedContent)
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testDeleteCertById() {
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        mvc.perform(
            MockMvcRequestBuilders.delete("/admin/cert/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn()
    }
}
