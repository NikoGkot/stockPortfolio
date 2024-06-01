package com.kotlinspring.controller.role

import com.kotlinspring.dto.RoleDTO
import com.kotlinspring.repository.RoleRepository
import com.kotlinspring.service.RoleService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/role")
class RoleController (
    private val roleRepository: RoleRepository,
    private val roleService: RoleService
) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun retrieveAllRoles(): List<RoleDTO> = roleService.retrieveAllRoles()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addRole(@RequestBody roleDTO: RoleDTO): RoleDTO{
        return roleService.addRole(roleDTO)
    }

    @DeleteMapping("{roleName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteRole(
        @PathVariable roleName: String
    ) = roleService.deleteRole(roleName)
}