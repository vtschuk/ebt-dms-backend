package de.ass73.tst.ebt.dms.backend.services

import de.ass73.ebt.dms.backend.entities.ProfessionEntity
import de.ass73.ebt.dms.backend.models.ProfessionModel
import de.ass73.ebt.dms.backend.repository.ProfessionRepo
import de.ass73.ebt.dms.backend.services.ProfessionService
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
@SpringBootTest(classes = [ProfessionService::class, ModelMapper::class])
@AutoConfigureMockMvc
class ProfessionServiceTest {
    @Autowired
    private lateinit var professionService: ProfessionService

    @MockBean
    private lateinit var professionRepo: ProfessionRepo

    @Test
    fun testGetAllProfessions() {
        //Given
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val name: String = Generators.generateString(15)
        val professionEntity = ProfessionEntity(id, name)
        val professionModel = ProfessionModel(id, name)

        //When
        Mockito.`when`(professionRepo.findAll()).thenReturn(java.util.List.of(professionEntity))
        val professionModels: List<ProfessionModel> = professionService.getAllProfessions(username)

        //Then
        Assertions.assertTrue(professionModels.size == 1)
        professionModels.forEach(Consumer { returnedModel: ProfessionModel ->
            Assertions.assertTrue(
                returnedModel.equals(professionModel)
            )
        })
    }

    @Test
    fun testGetProfessionById() {
        //Given
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val name: String = Generators.generateString(15)
        val professionEntity = ProfessionEntity(id, name)
        val professionModel = ProfessionModel(id, name)

        //When
        Mockito.`when`(professionRepo.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(professionEntity))
        val returnedModel: ProfessionModel = professionService.getProfessionById(id, username)

        //Then
        Assertions.assertTrue(returnedModel.equals(professionModel))
        Assertions.assertTrue(professionModel.equals(returnedModel))
    }

    @Test
    fun testCreateProfession() {
        //Given
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val name: String = Generators.generateString(15)
        val professionEntity = ProfessionEntity(id, name)
        val professionModel = ProfessionModel(id, name)

        //When
        Mockito.`when`(professionRepo.save(ArgumentMatchers.any())).thenReturn(professionEntity)
        val returnedModel: ProfessionModel = professionService.createProfession(professionModel, username)

        //Then
        Assertions.assertTrue(returnedModel.equals(professionModel))
        Assertions.assertTrue(professionModel.equals(returnedModel))
    }

    @Test
    fun testSaveProfession() {
        //Given
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val name: String = Generators.generateString(15)
        val professionEntity = ProfessionEntity(id, name)
        val professionModel = ProfessionModel(id, name)

        //When
        Mockito.`when`(professionRepo.existsById(ArgumentMatchers.anyLong())).thenReturn(true)
        Mockito.`when`(professionRepo.save(ArgumentMatchers.any())).thenReturn(professionEntity)
        val returnedModel: ProfessionModel = professionService.createProfession(professionModel, username)

        //Then
        Assertions.assertTrue(returnedModel.equals(professionModel))
        Assertions.assertTrue(professionModel.equals(returnedModel))
    }
}
