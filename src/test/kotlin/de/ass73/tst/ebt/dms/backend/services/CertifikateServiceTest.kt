package de.ass73.tst.ebt.dms.backend.services

import de.ass73.ebt.dms.backend.entities.CertificateEntity
import de.ass73.ebt.dms.backend.models.CertificateModel
import de.ass73.ebt.dms.backend.repository.CertRepo
import de.ass73.tst.ebt.dms.backend.tools.Generators
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*
import java.util.function.Consumer

@ExtendWith(SpringExtension::class)
@SpringBootTest(classes = [de.ass73.ebt.dms.backend.services.CertificateService::class, ModelMapper::class])
@AutoConfigureMockMvc
class CertifikateServiceTest {
    @Autowired
    private lateinit var certificateService: de.ass73.ebt.dms.backend.services.CertificateService

    @MockBean
    private lateinit var certRepo: CertRepo

    @Test
    fun testGetAllCertificates() {
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val name: String = Generators.generateString(15)
        val certificateEntity = CertificateEntity(
            id,
            name,
        )

        val certificateModel = CertificateModel(
            id,
            name
        )

        Mockito.`when`(certRepo.findAll()).thenReturn(java.util.List.of(certificateEntity))
        val certificateModelList: List<CertificateModel> = certificateService.getAllCertifcates(username)
        Assertions.assertTrue(certificateModelList.size == 1)
        certificateModelList.forEach(Consumer { returnedModel: CertificateModel ->
            Assertions.assertTrue(
                returnedModel.equals(certificateModel)
            )
        })
    }

    @Test
    fun testGetCertificateById() {
        //Given
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val name: String = Generators.generateString(15)
        val certificateEntity = CertificateEntity(id, name)
        val expectedCertificateModel = CertificateModel(id, name)
        //When
        Mockito.`when`(certRepo.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(certificateEntity))
        val returnedModel: CertificateModel = certificateService.getCertificateById(id, username)
        //Then
        Assertions.assertTrue(returnedModel.equals(expectedCertificateModel))
        Assertions.assertTrue(expectedCertificateModel.equals(returnedModel))
    }

    @Test
    fun testCreateCertificate() {
        //Given
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val name: String = Generators.generateString(15)
        val certificateEntity = CertificateEntity(id, name)
        val expectedCertificateModel = CertificateModel(id, name)
        //When
        Mockito.`when`(certRepo.save(ArgumentMatchers.any())).thenReturn(certificateEntity)
        val returnedModel: CertificateModel = certificateService.createCertificate(expectedCertificateModel, username)

        //Then
        Assertions.assertTrue(returnedModel.equals(expectedCertificateModel))
        Assertions.assertTrue(expectedCertificateModel.equals(returnedModel))
    }

    @Test
    fun testSaveCertificate() {
        //Given
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val name: String = Generators.generateString(15)
        val certificateEntity = CertificateEntity(id, name)
        val expectedCertificateModel = CertificateModel(id, name)

        //When
        Mockito.`when`(certRepo.existsById(ArgumentMatchers.anyLong())).thenReturn(true)
        Mockito.`when`(certRepo.save(ArgumentMatchers.any())).thenReturn(certificateEntity)
        val returnedModel: CertificateModel =
            certificateService.saveCertificate(id, expectedCertificateModel, username)

        //Then
        Assertions.assertTrue(returnedModel.equals(expectedCertificateModel))
        Assertions.assertTrue(expectedCertificateModel.equals(returnedModel))
    }
}
