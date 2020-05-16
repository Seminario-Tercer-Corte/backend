package com.seminario.api.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@Table(name = "organizations")
data class Organization(
        var name: String = "",
        var description: String = "",
        var ubication: String = "",
        var email: String = ""
) {
    @JsonIgnoreProperties("organization")
    @OneToMany(mappedBy = "organization", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var teams: List<Team> = emptyList()

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}