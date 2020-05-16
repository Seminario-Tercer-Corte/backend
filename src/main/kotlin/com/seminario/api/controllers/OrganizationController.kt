package com.seminario.api.controllers

import com.seminario.api.dto.OrganizationDTO
import com.seminario.api.dto.TeamDTO
import com.seminario.api.models.Organization
import com.seminario.api.models.Team
import com.seminario.api.services.OrganizationService
import com.seminario.api.utils.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Constants.URL_BASE_ORGANIZATIONS)
class OrganizationController {

    @Autowired
    private val organizationService: OrganizationService? = null

    /**
     * Create a new user.
     * @param organization: OrganizationDTO
     * @return ResponseEntity
     */
    @PostMapping("/save")
    fun save(@RequestBody organization: OrganizationDTO): ResponseEntity<Any> {
        organizationService!!.save(Organization(
                name = organization.name,
                description = organization.description,
                email = organization.email,
                ubication = organization.ubication
        ))
        return ResponseEntity(HttpStatus.CREATED)
    }

    /**
     * Create a new user.
     * @param organization: OrganizationDTO
     * @return ResponseEntity
     */
    @PutMapping("/update")
    fun update(@RequestBody organization: OrganizationDTO): ResponseEntity<Any> {
        return ResponseEntity(organizationService!!.update(organization), HttpStatus.OK)
    }

    /**
     * Get all a Organizations.
     * @return ResponseEntity
     */
    @GetMapping("/all")
    fun all(): ResponseEntity<List<Organization>> {
        var lstOrganizations: List<Organization> = organizationService!!.all()
        return ResponseEntity(lstOrganizations, HttpStatus.OK)
    }

    /**
     * Get one Organization
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    fun findOrganization(@PathVariable(value = "id") OrganizationId: Long): ResponseEntity<Organization> {
        return ResponseEntity(organizationService!!.finByid(OrganizationId), HttpStatus.OK)
    }

    /**
     * Remove one Organization
     * @return ResponseEntity
     */
    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable(value = "id") OrganizationId: Long): ResponseEntity<Boolean> {
        return ResponseEntity(organizationService!!.delete(OrganizationId), HttpStatus.OK)
    }

}
