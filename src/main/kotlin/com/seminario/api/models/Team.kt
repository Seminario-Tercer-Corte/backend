package com.seminario.api.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import javax.persistence.*

@Entity
@Table(name = "teams")
data class Team(
        var name: String? = "",
        var description: String? = "",
        var game: String? = "",
        var ubication: String? = "",
        var email: String? = "",
        @JsonIgnoreProperties("teams")
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "organization_id")
        var organization: Organization? = null
) {
    @JsonIgnoreProperties("team")
    @OneToMany(mappedBy = "team", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var users: List<User> = emptyList()

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
}