package com.seminario.api.models

import javax.persistence.*

@Entity
@Table(name = "permissions")
data class Permission(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,
        var name: String = ""
)