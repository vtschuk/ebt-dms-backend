package de.ass73.ebt.tst.efile.backend.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import de.ass73.ebt.dms.backend.EbtDmsBackendApplication
import de.ass73.ebt.dms.backend.models.UploadDocFilesModel
import de.ass73.ebt.dms.backend.models.UploadDocFilesResponseModel
import de.ass73.ebt.dms.backend.repository.JWTokenRepository
import de.ass73.ebt.dms.backend.services.UploadDocFilesService
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
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@SpringBootTest(classes = arrayOf(EbtDmsBackendApplication::class))
@AutoConfigureMockMvc
class UploadDocFilesControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var uploadDocFilesService: UploadDocFilesService

    @MockBean
    private lateinit var loginTools: LoginTools

    @MockBean
    private lateinit var jwTokenRepository: JWTokenRepository


    var objectMapper: ObjectMapper = ObjectMapper()
    private val username: String = Generators.generateString(8)

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testGetAllUploadDocFiles() {
        val uploadDocFilesModel = UploadDocFilesModel(
            Random().nextLong(),
            Random().nextLong(),
            Generators.generateString(10),
            Generators.generateString(10),
            byteArrayOf()
        )
        Mockito.`when`(uploadDocFilesService.getAllUploadDocFiles(ArgumentMatchers.anyString()))
            .thenReturn(listOf(uploadDocFilesModel))
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        val result: MvcResult = mvc.perform(
            MockMvcRequestBuilders.get("/uploaddoc/all")
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
        ).andExpect(MockMvcResultMatchers.status().isFound()).andReturn()
        val returnValue: String = result.getResponse().getContentAsString()
        val expectedValue: String = objectMapper.writeValueAsString(listOf(uploadDocFilesModel))
        Assertions.assertEquals(expectedValue, returnValue)
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testGetUploadDocFilesByPersonID() {
        val uploadDocFilesModel: UploadDocFilesModel = UploadDocFilesModel(
            Random().nextLong(),
            Random().nextLong(),
            Generators.generateString(10),
            Generators.generateString(10),
            byteArrayOf()
        )

        Mockito.`when`(
            uploadDocFilesService.getUploadDocFilesByPersonID(
                ArgumentMatchers.anyLong(),
                ArgumentMatchers.anyString()
            )
        ).thenReturn(
            listOf(uploadDocFilesModel)
        )
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        val result: MvcResult = mvc.perform(
            MockMvcRequestBuilders.get("/uploaddoc/all/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
        )
            .andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
        val returnValue: String = result.getResponse().getContentAsString()
        val expectedValue: String = objectMapper.writeValueAsString(listOf(uploadDocFilesModel))
        Assertions.assertEquals(expectedValue, returnValue)
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testGetUploadDocFileById() {
        //Given
        val uploadDocFilesModel = UploadDocFilesModel(
            Random().nextLong(),
            Random().nextLong(),
            Generators.generateString(10),
            MediaType.APPLICATION_OCTET_STREAM_VALUE,
            byteArrayOf()
        )

        //When
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        Mockito.`when`(uploadDocFilesService.getUploadFile(1L, username))
            .thenReturn(uploadDocFilesModel)

        //Them
        mvc.perform(
            MockMvcRequestBuilders.get("/uploaddoc/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
        )
            .andExpect(MockMvcResultMatchers.status().isOk())
            //todo
            //.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE))
            .andExpect(MockMvcResultMatchers.content().bytes(uploadDocFilesModel.filedata))
            .andReturn()
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testCreateUploadDocFile() {
        //Given
        val uploadDocFilesModel: UploadDocFilesModel = UploadDocFilesModel(
            Random().nextLong(),
            Random().nextLong(),
            Generators.generateString(10),
            Generators.generateString(10),
            byteArrayOf()

        )

        val uploadDocFilesResponseModel = UploadDocFilesResponseModel(
            Generators.generateString(25)
        )

        val file = MockMultipartFile(
            "file",
            "hello.txt",
            MediaType.APPLICATION_OCTET_STREAM_VALUE,
            uploadDocFilesModel.filedata
        )

        //When
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        Mockito.`when`(
            uploadDocFilesService.createUploadFile(
                1,
                file,
                username
            )
        ).thenReturn(uploadDocFilesResponseModel)

        //Then
        mvc.perform(
            MockMvcRequestBuilders.multipart(HttpMethod.POST, "/uploaddoc/1")
                .file(file)
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(uploadDocFilesModel))
        )
            .andExpect(MockMvcResultMatchers.status().isCreated())
    }

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testDeleteUpdateById() {
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.anyString())).thenReturn(username)
        mvc.perform(
            MockMvcRequestBuilders.delete("/uploaddoc/1")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer Test")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
        ).andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn()
    }
}
