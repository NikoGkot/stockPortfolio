package com.kotlinspring.service

import com.kotlinspring.dto.RoleDTO
import com.kotlinspring.dto.toDTO
import com.kotlinspring.entity.Role
import com.kotlinspring.repository.RoleRepository
import jakarta.transaction.Transactional
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class RoleService(
    private val roleRepository: RoleRepository
) {

    companion object : KLogging()

    fun retrieveAllRoles(): List<RoleDTO> {
        val roles = roleRepository.findAll()
        return roles
            .map {
                RoleDTO(it.id, it.name)
            }
    }

    @Transactional
    fun addRole(roleDTO: RoleDTO): RoleDTO {

        // Ensure the role name has the prefix "ROLE_"
        val roleName = if (roleDTO.name.startsWith("ROLE_")) {
            roleDTO.name
        } else {
            "ROLE_${roleDTO.name}"
        }

        val role = roleDTO.let {
            Role(it.id, name = roleName)
        }

        roleRepository.save(role)
        logger.info { "Saved role is:  $role" }
        return role.toDTO()

    }

    @Transactional
    fun deleteRole(roleName: String): Any {
        val existingRoles = roleRepository.findAllByName(roleName)

        return if (existingRoles.isNotEmpty()) {
            roleRepository.deleteAll(existingRoles)
            logger.info { "Deleted ${existingRoles.size} roles with name $roleName" }
        } else {
            logger.info { "No roles found with name $roleName" }
        }
    }

}