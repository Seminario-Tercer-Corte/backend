package com.seminario.api.dto

data class UserDTO(
        var id: Long?,
        var name: String,
        var username: String?,
        var password: String?,
        var picture: String?,
        var team: TeamDTO?,
        var job: String?
)