package com.seminario.api.services

import com.seminario.api.dto.TeamDTO
import com.seminario.api.exceptions.AlreadyExists
import com.seminario.api.exceptions.Database
import com.seminario.api.models.Team
import com.seminario.api.models.User
import com.seminario.api.repositories.OrganizationRepository
import com.seminario.api.repositories.TeamRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TeamService {

    @Autowired
    val teamRepository: TeamRepository? = null

    @Autowired
    val organizationRepository: OrganizationRepository? = null

    /**
     * Create a new user
     * @param team: Team
     * @throws AlreadyExists
     * @throws Database
     */
    @Throws(Database::class, AlreadyExists::class)
    fun save(team: Team) {
        try {
            teamRepository!!.save(team)
        } catch (e: Exception) {
            throw Database(e.message)
        }
    }

    /**
     * Create a new user
     * @param team: Team
     * @throws AlreadyExists
     * @throws Database
     */
    @Throws(Database::class, AlreadyExists::class)
    fun update(team: TeamDTO): Team {
        try {
            return teamRepository!!.findById(team.id!!).map { existingTeam ->
                existingTeam.description = team.description
                existingTeam.email = team.email
                existingTeam.game = team.game
                existingTeam.name = team.name
                existingTeam.ubication = team.ubication
                existingTeam.organization = organizationRepository!!.findById(team.organization_id!!).get()
                teamRepository!!.save(existingTeam)
            }.orElse(null)
        } catch (e: Exception) {
            throw Database(e.message)
        }
    }

    /**
     * Get all Teams
     * @throws AlreadyExists
     * @throws Database
     */
    @Throws(Database::class, AlreadyExists::class)
    fun all(): List<Team> {
        try {
            return teamRepository!!.findAll()
        } catch (e: Exception) {
            throw Database(e.message)
        }
    }

    /**
     * Find By Id Team
     * @throws AlreadyExists
     * @throws Database
     */
    @Throws(Database::class, AlreadyExists::class)
    fun finByid(id: Long): Team {
        try {
            return teamRepository!!.findById(id).get()
        } catch (e: Exception) {
            throw Database(e.message)
        }
    }

    /**
     * Find Users by Team
     * @throws AlreadyExists
     * @throws Database
     */
    @Throws(Database::class, AlreadyExists::class)
    fun users(id: Long): List<User> {
        try {
            return teamRepository!!.findById(id).get().users
        } catch (e: Exception) {
            throw Database(e.message)
        }
    }

    /**
     * Get all Teams
     * @throws Database
     */
    @Throws(Database::class, AlreadyExists::class)
    fun delete(id: Long): Boolean {
        try {
            return teamRepository!!.findById(id).map { existingTeam ->
                teamRepository!!.delete(existingTeam)
                true
            }.orElse(false)
        } catch (e: Exception) {
            throw Database(e.message)
        }
    }
}
