package de.ass73.ebt.dms.backend.services

import de.ass73.ebt.dms.backend.models.ChangeRoleModel
import de.ass73.ebt.efile.backend.models.RoleModel
import de.ass73.ebt.dms.backend.repository.RoleRepo
import org.modelmapper.ModelMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class RoleService(
    @Autowired
    private val roleRepo: RoleRepo,

    @Autowired
    private val modelMapper: ModelMapper
) {

    /* used by group:  admin */
    fun getAllRoles(username: String?): List<RoleModel> {
        return roleRepo.findAll().stream()
            .map { model -> modelMapper.map(model, RoleModel::class.java) }
            .collect(Collectors.toList())
    }

    /* used by group:  admin */
    fun changeRole(login: String?, changeRoleModel: ChangeRoleModel?): RoleModel? {
        return null
    }
}
