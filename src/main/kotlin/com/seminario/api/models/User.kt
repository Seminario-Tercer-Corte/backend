package com.seminario.api.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.stream.Collectors
import javax.persistence.*

@Entity
@Table(name = "users")
data class User(
        var name: String,
        var picture: String?,
        var job: String?,
        private var username: String,
        private var password: String,
        @JsonIgnoreProperties("users")
        @ManyToOne(fetch = FetchType.EAGER)
        @JoinColumn(name = "team_id")
        var team: Team? = null
) : UserDetails {

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = [JoinColumn(name = "id_user")],
            inverseJoinColumns = [JoinColumn(name = "id_role")]
    )
    var roles: List<Role>? = null

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    /**
     * Get a list with permissions name.
     * @return ArrayList<String>
     */
    fun getScopes(): ArrayList<String> {
        val scopes: ArrayList<String> = ArrayList()
        val collection: MutableList<Permission>? = ArrayList()

        roles?.forEach { role -> collection?.addAll(role.permissions!!) }

        collection?.forEach { permission ->
            if (!scopes.contains(permission.name)) {
                scopes.add(permission.name)
            }
        }

        return scopes
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return getScopes()
                .stream()
                .map { s -> SimpleGrantedAuthority(s) }
                .collect(Collectors.toList())
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun getUsername(): String {
        return this.username
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun getPassword(): String {
        return this.password
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}


