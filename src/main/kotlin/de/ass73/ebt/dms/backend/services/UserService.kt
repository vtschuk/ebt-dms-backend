package de.ass73.ebt.dms.backend.services

import de.ass73.ebt.dms.backend.entities.UserEntity
import de.ass73.ebt.efile.backend.models.UserModel
import de.ass73.ebt.dms.backend.repository.LoginRepository
import de.ass73.ebt.dms.backend.services.exceptions.BadServiceCallException
import de.ass73.ebt.dms.backend.services.exceptions.ResourceNotFoundException
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ExceptionHandler

@Service
class UserService(
    @Autowired
    private val loginRepository: LoginRepository,

    @Autowired
    private val mapper: ModelMapper
) {

    fun getAllUsers(username: String): List<UserModel> {
        return loginRepository.findAll()
            .stream()
            .map { user -> mapper.map(user, UserModel::class.java) }
            .toList()
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun getUserById(id: String, username: String?): UserModel {
        return loginRepository.findById(id.toLong())
            .map { user -> mapper.map(user, UserModel::class.java) }
            .orElseThrow { BadServiceCallException("User with id: $id not found") }
    }

    fun updateUser(id: Long, userModel: UserModel, username: String): UserModel {
        if (loginRepository.existsById(id)) {
            userModel.id = id
            val userEntity: UserEntity = mapper.map(userModel, UserEntity::class.java)
            val savedUser: UserEntity = loginRepository.save(userEntity)
            return mapper.map(savedUser, UserModel::class.java)!!
        } else {
            throw BadServiceCallException("User with id: $id not found")
        }
    }

    fun deleteUser(id: String, username: String?) {
        if (loginRepository.existsById(id.toLong())) {
            loginRepository.deleteById(id.toLong())
        } else {
            throw BadServiceCallException("User with id: $id not found")
        }
    }
}