package com.seminario.api.controllers

import com.seminario.api.dto.OrganizationDTO
import com.seminario.api.dto.TeamDTO
import com.seminario.api.models.Organization
import com.seminario.api.models.Team
import com.seminario.api.models.User
import com.seminario.api.services.OrganizationService
import com.seminario.api.services.TeamService
import com.seminario.api.utils.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Constants.URL_BASE_TEAMS)
class TeamController {

    @Autowired
    private val teamService: TeamService? = null

    @Autowired
    private val organizationService: OrganizationService? = null

    /**
     * Create a team.
     * @param team: TeamDTO
     * @return ResponseEntity
     */
    @PostMapping("/save")
    fun save(@RequestBody team: TeamDTO): ResponseEntity<Any> {
        teamService!!.save(Team(
                name = team.name,
                description = team.description,
                email = team.email,
                game = team.game,
                ubication = team.ubication,
                organization = organizationService!!.finByid(team.organization_id!!)
        ))
        return ResponseEntity(HttpStatus.CREATED)
    }

    /**
     * Update a team.
     * @param team: TeamDTO
     * @return ResponseEntity
     */
    @PutMapping("/update")
    fun update(@RequestBody team: TeamDTO): ResponseEntity<Any> {
        return ResponseEntity(teamService!!.update(team), HttpStatus.OK)
    }

    /**
     * Get all Teams
     * @return ResponseEntity
     */
    @GetMapping("/all")
    fun all(): ResponseEntity<List<Team>> {
        var lstTeams: List<Team> = teamService!!.all()
        return ResponseEntity(lstTeams, HttpStatus.OK)
    }

    /**
     * Get one Team
     * @return ResponseEntity
     */
    @GetMapping("/{id}")
    fun findTeam(@PathVariable(value = "id") TeamId: Long): ResponseEntity<Team> {
        return ResponseEntity(teamService!!.finByid(TeamId), HttpStatus.OK)
    }

    /**
     * Get Users By Team
     * @return ResponseEntity
     */
    @GetMapping("/{id}/users")
    fun findUsers(@PathVariable(value = "id") TeamId: Long): ResponseEntity<List<User>> {
        return ResponseEntity(teamService!!.users(TeamId), HttpStatus.OK)
    }

    /**
     * Remove one Team
     * @return ResponseEntity
     */
    @DeleteMapping("/delete/{id}")
    fun delete(@PathVariable(value = "id") TeamId: Long): ResponseEntity<Boolean> {
        return ResponseEntity(teamService!!.delete(TeamId), HttpStatus.OK)
    }
}
