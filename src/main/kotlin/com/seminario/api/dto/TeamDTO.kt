package com.seminario.api.dto

data class TeamDTO(
        var id: Long?,
        var name: String?,
        var description: String?,
        var game: String?,
        var ubication: String?,
        var email: String?,
        var organization_id: Long?
)