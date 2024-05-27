package de.ass73.ebt.dms.backend.services


import de.ass73.ebt.dms.backend.entities.ExpertiseEntity
import de.ass73.ebt.dms.backend.models.ExpertiseModel
import de.ass73.ebt.dms.backend.repository.ExpertiseRepo
import de.ass73.ebt.dms.backend.tools.Generators
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
@SpringBootTest(classes = [ExpertiseService::class, ModelMapper::class])
@AutoConfigureMockMvc
class ExpertiseServiceTest {

    @Autowired
    private lateinit var expertiseService: ExpertiseService

    @MockBean
    private lateinit var expertiseRepo: ExpertiseRepo

    @Test
    fun testGetAllExpertices() {
        //Given
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val name: String = Generators.generateString(15)
        val expertiseModel = ExpertiseModel(id, name)

        val expertiseEntity = ExpertiseEntity(id, name)

        //When
        Mockito.`when`(expertiseRepo.findAll()).thenReturn(java.util.List.of(expertiseEntity))
        val returnedModels: List<ExpertiseModel> = expertiseService.getAllExpertises(username)

        //Then
        Assertions.assertTrue(returnedModels.size == 1)
        returnedModels.forEach(Consumer<ExpertiseModel> { returnedModel: ExpertiseModel ->
            Assertions.assertTrue(
                returnedModel.equals(expertiseModel)
            )
        })
    }

    @Test
    fun testGetExperticeById() {
        //Given
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val name: String = Generators.generateString(15)
        val expertiseModel = ExpertiseModel(id, name)
        val expertiseEntity = ExpertiseEntity(id, name)

        //When
        Mockito.`when`(expertiseRepo.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(expertiseEntity))
        val returnedModel: ExpertiseModel = expertiseService.getExpertiseById(id, username)

        //Then
        Assertions.assertTrue(returnedModel.equals(expertiseModel))
        Assertions.assertTrue(expertiseModel.equals(returnedModel))
    }

    @Test
    fun testCreateExpertise() {
        //Given
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val name: String = Generators.generateString(15)
        val expertiseModel = ExpertiseModel(id, name)
        val expertiseEntity = ExpertiseEntity(id, name)

        //When
        Mockito.`when`(expertiseRepo.save(ArgumentMatchers.any())).thenReturn(expertiseEntity)
        val returnedModel: ExpertiseModel = expertiseService.createExpertise(expertiseModel, username)

        //Then
        Assertions.assertTrue(returnedModel.equals(expertiseModel))
        Assertions.assertTrue(expertiseModel.equals(returnedModel))
    }

    @Test
    fun testSaveexperticeModel() {
        //Given
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val name: String = Generators.generateString(15)
        val expertiseModel = ExpertiseModel(id, name)
        val expertiseEntity = ExpertiseEntity(id, name)

        //When
        Mockito.`when`(expertiseRepo.existsById(ArgumentMatchers.anyLong())).thenReturn(true)
        Mockito.`when`(expertiseRepo.save(ArgumentMatchers.any())).thenReturn(expertiseEntity)
        val returnedModel: ExpertiseModel = expertiseService.saveExpertise(id, expertiseModel, username)

        //Then
        Assertions.assertTrue(returnedModel.equals(expertiseModel))
        Assertions.assertTrue(expertiseModel.equals(returnedModel))
    }
}
