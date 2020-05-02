package com.seminario.api.models

import javax.persistence.*

@Entity
@Table(name = "roles")
data class Role(
        var name: String
) {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = 0

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
                name = "roles_permissions",
                joinColumns = [JoinColumn(name = "id_role")],
                inverseJoinColumns = [JoinColumn(name = "id_permission")]
        )
        var permissions: List<Permission>? = null
}