package de.ass73.ebt.tst.efile.backend.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import de.ass73.ebt.dms.backend.EbtDmsBackendApplication
import de.ass73.ebt.dms.backend.entities.image.PhotoImage
import de.ass73.ebt.dms.backend.models.image.PhotoUploadResponse
import de.ass73.ebt.dms.backend.repository.JWTokenRepository
import de.ass73.ebt.dms.backend.services.image.UploadPhotoImageService
import de.ass73.ebt.dms.backend.services.users.LoginTools
import de.ass73.tst.ebt.dms.backend.tools.Generators
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.util.*

@SpringBootTest(classes = arrayOf(EbtDmsBackendApplication::class))
@AutoConfigureMockMvc
class UploadPhotoImageControllerTest {
    @Autowired
    private lateinit var mvc: MockMvc

    @MockBean
    private lateinit var uploadPhotoImageService: UploadPhotoImageService

    @MockBean
    private lateinit var loginTools: LoginTools

    @MockBean
    private lateinit var jwTokenRepository: JWTokenRepository


    var objectMapper: ObjectMapper = ObjectMapper()
    private val username: String = Generators.generateString(8)

    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testUpload() {
        //Given
        val photoImage = PhotoImage(
            Random().nextLong(),
            Random().nextLong(),
            Generators.generateString(10),
            Generators.generateString(10),
            byteArrayOf()
        )
        val uploadResponse: PhotoUploadResponse = PhotoUploadResponse()
        val file = MockMultipartFile(
            "file",
            "hello.txt",
            MediaType.APPLICATION_OCTET_STREAM_VALUE,
            photoImage.imageData
        )

        //When
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.any())).thenReturn(username)
        Mockito.`when`(
            uploadPhotoImageService.uploadImage(
                1L,
                file,
                username
            )
        ).thenReturn(uploadResponse)

        //Then
        mvc.perform(
            MockMvcRequestBuilders.multipart(HttpMethod.POST, "/uploadphoto/1")
                .file(file)
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer test")
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn()
    }


    @WithMockUser
    @Test
    @Throws(Exception::class)
    fun testGetImageByFileId() {
        //Givem
        val photoImage = PhotoImage(
            Random().nextLong(),
            Random().nextLong(),
            Generators.generateString(10),
            MediaType.APPLICATION_OCTET_STREAM_VALUE,
            byteArrayOf()
        )
        //When
        Mockito.`when`(loginTools.extractUsername(ArgumentMatchers.any())).thenReturn(username)
        Mockito.`when`(
            uploadPhotoImageService.getImageByFileId(
                1L,
                username
            )
        ).thenReturn(photoImage)

        //Then
        val result = mvc.perform(
            MockMvcRequestBuilders.get("/uploadphoto/1")
                .with(csrf())
                .header(HttpHeaders.AUTHORIZATION, "Bearer test")
        ).andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn()
        //todo check - content
    }
}
