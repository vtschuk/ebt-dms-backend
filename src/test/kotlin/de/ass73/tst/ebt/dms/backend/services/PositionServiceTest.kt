package de.ass73.tst.ebt.dms.backend.services

import de.ass73.ebt.dms.backend.entities.PositionEntity
import de.ass73.ebt.dms.backend.entities.UserEntity
import de.ass73.ebt.dms.backend.models.PositionModel
import de.ass73.ebt.dms.backend.repository.LoginRepository
import de.ass73.ebt.dms.backend.repository.PositionRepo
import de.ass73.ebt.dms.backend.services.PositionService
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
@SpringBootTest(classes = [PositionService::class, ModelMapper::class])
@AutoConfigureMockMvc
class PositionServiceTest {
    @Autowired
    private lateinit var positionService: PositionService

    @MockBean
    private lateinit var positionRepo: PositionRepo

    @MockBean
    private lateinit var loginRepository: LoginRepository


    @Test
    fun testGetAllPositions() {
        //Given
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val name: String = Generators.generateString(15)
        val positionEntity = PositionEntity(id, name)

        val positionModel = PositionModel(id, name)
        val userEntity = UserEntity(
            Random().nextLong(),
            username,
            Generators.generateString(10),
            "admin"
        )

        //When
        Mockito.`when`(positionRepo.findAll()).thenReturn(java.util.List.of(positionEntity))
        Mockito.`when`(loginRepository.findByUsername2(ArgumentMatchers.anyString()))
            .thenReturn(Optional.of(userEntity))
        val positionModels: List<PositionModel> = positionService.getAllPositions(username)

        //Then
        Assertions.assertTrue(positionModels.size == 1)
        positionModels.forEach(Consumer<PositionModel> { returnedModel: PositionModel ->
            Assertions.assertTrue(
                returnedModel.equals(positionModel)
            )
        })
    }

    @Test
    fun testGetPositionById() {
        //Given
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val name: String = Generators.generateString(15)
        val positionEntity = PositionEntity(
            id,
            name
        )

        val positionModel = PositionModel(id, name)

        //When
        Mockito.`when`(positionRepo.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(positionEntity))
        val returnedModel: PositionModel = positionService.getPositionById(id, username)

        //Then
        Assertions.assertTrue(returnedModel.equals(positionModel))
        Assertions.assertTrue(positionModel.equals(returnedModel))
    }

    @Test
    fun testCreatePosition() {
        //Given
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val name: String = Generators.generateString(15)
        val positionEntity = PositionEntity(id, name)
        val positionModel = PositionModel(id, name)

        // todo: mapping will not be tested
        //When
        Mockito.`when`(positionRepo.save(ArgumentMatchers.any())).thenReturn(positionEntity)
        val returnedModel: PositionModel = positionService.createPosition(positionModel, username)

        //Then
        Assertions.assertTrue(returnedModel.equals(positionModel))
        Assertions.assertTrue(positionModel.equals(returnedModel))
    }

    @Test
    fun testSavePosition() {
        //Given
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val name: String = Generators.generateString(15)
        val positionEntity = PositionEntity(id, name)
        val positionModel = PositionModel(id, name)

        //When
        Mockito.`when`(positionRepo.existsById(ArgumentMatchers.anyLong())).thenReturn(true)
        Mockito.`when`(positionRepo.save(ArgumentMatchers.any())).thenReturn(positionEntity)
        val returnedModel: PositionModel = positionService.savePosition(id, positionModel, username)

        //Then
        Assertions.assertTrue(returnedModel.equals(positionModel))
        Assertions.assertTrue(positionModel.equals(returnedModel))
    }
}
