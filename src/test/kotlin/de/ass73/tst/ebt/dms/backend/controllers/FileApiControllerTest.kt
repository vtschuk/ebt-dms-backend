package de.ass73.tst.ebt.dms.backend.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import de.ass73.ebt.dms.backend.EbtDmsBackendApplication
import de.ass73.ebt.dms.backend.models.AdressModel
import de.ass73.ebt.dms.backend.models.FileModel
import de.ass73.ebt.dms.backend.repository.JWTokenRepository
import de.ass73.ebt.dms.backend.services.FileApiServiceInterface
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
import java.sql.Date
import java.util.*


@SpringBootTest(classes = arrayOf(EbtDmsBackendApplication::class))
@AutoConfigureMockMvc
class FileApiControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var fileApiServiceInterface: FileApiServiceInterface

    @MockBean
    private lateinit var loginTools: LoginTools

    @MockBean
    private lateinit var jwTokenRepository: JWTokenRepository

    private val username: String = Generators.generateString(8)

    @Autowired
    private lateinit var objectMapper: ObjectMapper


    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testGetAllPersons() {
        val fileModel = FileModel(
            Random().nextLong(),
            Generators.generateString(10),
            Generators.generateString(10),
            Generators.generateString(10),
            Date(Calendar.getInstance().time.time),
            Generators.generateString(10),
            //byteArrayOf(),
            AdressModel(
                Random().nextLong(),
                Random().nextInt(),
                Generators.generateString(8),
                Generators.generateString(10),
                Generators.generateString(11),
                Generators.generateString(11),
                Random().nextInt()
            )
        )

        Mockito.`when`(fileApiServiceInterface.getAllFiles(ArgumentMatchers.anyString()))
            .thenReturn(listOf(fileModel))
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)

        val result: MvcResult = mvc.perform(
            MockMvcRequestBuilders.get("/file/all")
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
        val returnValue: String = result.getResponse().getContentAsString()
        val expectedValue: String = objectMapper.writeValueAsString(listOf(fileModel))
        Assertions.assertEquals(expectedValue, returnValue)
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testGetPersonById() {
        val fileModel = FileModel(
            Random().nextLong(),
            Generators.generateString(10),
            Generators.generateString(10),
            Generators.generateString(10),
            Date(Calendar.getInstance().time.time),
            Generators.generateString(10),
            //byteArrayOf(),
            AdressModel(
                Random().nextLong(),
                Random().nextInt(),
                Generators.generateString(8),
                Generators.generateString(10),
                Generators.generateString(11),
                Generators.generateString(11),
                Random().nextInt()
            )
        )

        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        Mockito.`when`(
            fileApiServiceInterface.getFileById(
                1L,
                username
            )
        ).thenReturn(fileModel)

        val result: MvcResult = mvc.perform(
            MockMvcRequestBuilders.get("/file/get/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
        )
            .andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
        val returnValue: String = result.getResponse().getContentAsString()

        val expectedValue: String = objectMapper.writeValueAsString(fileModel)
        Assertions.assertEquals(expectedValue, returnValue)
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testCreatePerson() {
        val fileModel = FileModel(
            Random().nextLong(),
            Generators.generateString(10),
            Generators.generateString(10),
            Generators.generateString(10),
            Date(Calendar.getInstance().time.time),
            Generators.generateString(10),
            //byteArrayOf(),
            AdressModel(
                Random().nextLong(),
                Random().nextInt(),
                Generators.generateString(8),
                Generators.generateString(10),
                Generators.generateString(11),
                Generators.generateString(11),
                Random().nextInt()
            )
        )

        //When
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        Mockito.`when`(fileApiServiceInterface.create(fileModel, username)).thenReturn(fileModel)

        val result = mvc.perform(
            MockMvcRequestBuilders.post("/file/create").with(csrf())
                //.accept(MediaType.APPLICATION_JSON_VALUE)
                .header(HttpHeaders.AUTHORIZATION, "Bearer test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(fileModel))
        ).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn()

        //todo
        /*        val returnedContent: String = result.getResponse().getContentAsString()
                val expectedContent: String = objectMapper.writeValueAsString(personModel)
                Assertions.assertEquals(expectedContent, returnedContent)
        */
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testSavePerson() {
        val fileModel = FileModel(
            Random().nextLong(),
            Generators.generateString(10),
            Generators.generateString(10),
            Generators.generateString(10),
            Date(Calendar.getInstance().getTime().getTime()),
            Generators.generateString(10),
            //byteArrayOf(),
            AdressModel(
                Random().nextLong(),
                Random().nextInt(),
                Generators.generateString(8),
                Generators.generateString(10),
                Generators.generateString(11),
                Generators.generateString(11),
                Random().nextInt()
            )
        )
        //When
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.any())).thenReturn(username)
        Mockito.`when`(
            fileApiServiceInterface.save(
                1L,
                fileModel,
                username
            )
        ).thenReturn(fileModel)

        //Then
        val result: MvcResult = mvc.perform(
            MockMvcRequestBuilders.put("/file/save/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(fileModel))
        ).andExpect(MockMvcResultMatchers.status().isCreated()).andReturn()

        //todo
        /*
                val returnedContent: String = result.getResponse().getContentAsString()
                val expectedContent: String = objectMapper.writeValueAsString(personModel)
                Assertions.assertEquals(expectedContent, returnedContent)
        */
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testDeletePerson() {
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        mvc.perform(
            MockMvcRequestBuilders.delete("/file/delete/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn()
    }

}
