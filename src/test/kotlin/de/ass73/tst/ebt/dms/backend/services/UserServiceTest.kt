package de.ass73.tst.ebt.dms.backend.services

import de.ass73.ebt.dms.backend.entities.UserEntity
import de.ass73.ebt.dms.backend.models.UserModel
import de.ass73.ebt.dms.backend.repository.LoginRepository
import de.ass73.ebt.dms.backend.services.UserService
import de.ass73.tst.ebt.dms.backend.tools.Generators
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Disabled
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
@SpringBootTest(classes = [UserService::class, ModelMapper::class])
@AutoConfigureMockMvc
class UserServiceTest {
    @Autowired
    private lateinit var userService: UserService

    @MockBean
    private lateinit var loginRepository: LoginRepository

    @Disabled
    @Test
    fun testGetAllUsers() {
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val password: String = Generators.generateString(15)
        val userEntity = UserEntity(id, username, password, "admin")
        val userModel = UserModel(id, username, password, "admin")
        Mockito.`when`(loginRepository.findAll()).thenReturn(listOf(userEntity))
        val models: List<UserModel> = userService.getAllUsers(username)
        Assertions.assertTrue(models.size == 1)
        models.forEach(Consumer { model: UserModel -> Assertions.assertTrue(model.equals(userModel)) })
    }


    @Disabled
    @Test
    fun testGetUserById() {
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val password: String = Generators.generateString(15)
        val userEntity = UserEntity(id, username, password, "admin")
        val userModel = UserModel(id, username, password, "admin")
        Mockito.`when`(loginRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(userEntity))
        val returnedModel: UserModel = userService.getUserById(id.toString(), username)
        Assertions.assertTrue(returnedModel.equals(userModel))
    }

    @Disabled
    @Test
    fun testUpdateUser() {
        val username: String = Generators.generateString(10)
        val id = Random().nextLong()
        val password: String = Generators.generateString(15)
        val userEntity = UserEntity(id, username, password, "admin")
        val userModel = UserModel(id, username, password, "admin")
        Mockito.`when`(loginRepository.existsById(ArgumentMatchers.anyLong())).thenReturn(true)
        Mockito.`when`(loginRepository.save(ArgumentMatchers.any())).thenReturn(userEntity)
        val returnedModel: UserModel = userService.updateUser(id, userModel, username)
        Assertions.assertTrue(returnedModel.equals(userModel))
    }
}
