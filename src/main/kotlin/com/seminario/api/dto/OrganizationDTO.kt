package com.seminario.api.dto

data class OrganizationDTO(
        var id: Long?,
        var name: String,
        var description: String,
        var email: String,
        var ubication: String
)