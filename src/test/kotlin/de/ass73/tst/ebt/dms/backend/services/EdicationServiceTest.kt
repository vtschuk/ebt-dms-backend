package de.ass73.tst.ebt.dms.backend.services

import de.ass73.ebt.dms.backend.entities.EducationEntity
import de.ass73.ebt.dms.backend.models.EducationModel
import de.ass73.ebt.dms.backend.repository.EducationRepo
import de.ass73.ebt.dms.backend.services.EducationService
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
@SpringBootTest(classes = [EducationService::class, ModelMapper::class])
@AutoConfigureMockMvc
class EdicationServiceTest {
    @Autowired
    private lateinit var educationService: EducationService

    @MockBean
    private lateinit var educationRepo: EducationRepo

    @Test
    fun testGetAllEducations() {
        //Given
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val name: String = Generators.generateString(15)
        val educationEntity = EducationEntity(
            id,
            name
        )
        val educationModel = EducationModel(id, name)

        //When
        Mockito.`when`(educationRepo.findAll()).thenReturn(listOf(educationEntity))
        val educationModelList: List<EducationModel> = educationService.getAllEducations(username)

        //Then
        Assertions.assertTrue(educationModelList.size == 1)
        educationModelList.forEach(Consumer { returnedModel: EducationModel ->
            Assertions.assertTrue(
                returnedModel.equals(educationModel)
            )
        })
    }

    @Test
    fun testGetEducationById() {
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val name: String = Generators.generateString(15)
        val educationEntity = EducationEntity(id, name)
        val educationModel = EducationModel(id, name)

        //When
        Mockito.`when`(educationRepo.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(educationEntity))
        val returnedModel: EducationModel = educationService.getEducationById(id, username)
        //Then
        Assertions.assertTrue(returnedModel.equals(educationModel))
        Assertions.assertTrue(educationModel.equals(returnedModel))
    }

    @Test
    fun testCreateEducation() {
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val name: String = Generators.generateString(15)
        val educationEntity = EducationEntity(id, name)
        val educationModel = EducationModel(id, name)
        //When
        Mockito.`when`(educationRepo.save(ArgumentMatchers.any())).thenReturn(educationEntity)
        val returnedModel: EducationModel = educationService.createEducation(educationModel, username)

        //Then
        Assertions.assertTrue(returnedModel.equals(educationModel))
        Assertions.assertTrue(educationModel.equals(returnedModel))
    }

    @Test
    fun testSaveEducation() {
        //Given
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val name: String = Generators.generateString(15)
        val educationEntity = EducationEntity(id, name)
        val educationModel = EducationModel(id, name)
        //When
        Mockito.`when`(educationRepo.existsById(ArgumentMatchers.anyLong())).thenReturn(true)
        Mockito.`when`(educationRepo.save(ArgumentMatchers.any())).thenReturn(educationEntity)
        val returnedModel: EducationModel = educationService.saveEducation(id, educationModel, username)

        //Then
        Assertions.assertTrue(returnedModel.equals(educationModel))
        Assertions.assertTrue(educationModel.equals(returnedModel))
    }
}
