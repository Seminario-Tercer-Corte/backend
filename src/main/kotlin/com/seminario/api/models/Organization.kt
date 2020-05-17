package com.seminario.api.models

import javax.persistence.*

@Entity
@Table(name = "permissions")
data class Organization(
        var name: String?,
        var description: String?,
        var email: String,
        var ubication: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = 0
}