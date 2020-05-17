package com.seminario.api.services

import com.seminario.api.dto.OrganizationDTO
import com.seminario.api.dto.TeamDTO
import com.seminario.api.utils.JwtUtil
import com.seminario.api.repositories.RoleRepository
import com.seminario.api.repositories.UserRepository
import com.seminario.api.models.User
import com.seminario.api.models.Role
import com.seminario.api.exceptions.AlreadyExists
import com.seminario.api.exceptions.Database
import com.seminario.api.exceptions.NotFound
import com.seminario.api.models.Organization
import com.seminario.api.models.Team
import com.seminario.api.repositories.OrganizationRepository
import com.seminario.api.repositories.TeamRepository
import com.seminario.api.utils.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.lang.Exception
import java.util.*

@Service
class OrganizationService {

    @Autowired
    val organizationRepository: OrganizationRepository? = null

    /**
     * Create a new Organization
     * @param organization: Organization
     * @throws AlreadyExists
     * @throws Database
     */
    @Throws(Database::class, AlreadyExists::class)
    fun save(organization: Organization) {
        try {
            organizationRepository!!.save(organization)
        } catch (e: Exception) {
            throw Database(e.message)
        }
    }

    /**
     * Update a new Organization
     * @param organization: Organization
     * @throws Database
     */
    @Throws(Database::class, AlreadyExists::class)
    fun update(organization: OrganizationDTO): Organization {
        try {
            return organizationRepository!!.findById(organization.id!!).map { existingOrganization ->

                existingOrganization.description = organization.description
                existingOrganization.email = organization.email
                existingOrganization.name = organization.name
                existingOrganization.ubication = organization.ubication

                organizationRepository!!.save(existingOrganization)
            }.orElse(null)
        } catch (e: Exception) {
            throw Database(e.message)
        }
    }

    /**
     * Find all Organizations
     * @throws Database
     */
    @Throws(Database::class)
    fun all(): List<Organization> {
        try {
            return organizationRepository!!.findAll()
        } catch (e: Exception) {
            throw Database(e.message)
        }
    }

    /**
     * Find By Id Organizations
     * @throws Database
     */
    @Throws(Database::class)
    fun finByid(id: Long): Organization {
        try {
            return organizationRepository!!.findById(id).get()
        } catch (e: Exception) {
            throw Database(e.message)
        }
    }

    /**
     * Remove one Organization
     * @throws Database
     */
    @Throws(Database::class)
    fun delete(id: Long): Boolean {
        try {
            return organizationRepository!!.findById(id).map { existingOrganization ->
                organizationRepository!!.delete(existingOrganization)
                true
            }.orElse(false)
        } catch (e: Exception) {
            throw Database(e.message)
        }
    }
}