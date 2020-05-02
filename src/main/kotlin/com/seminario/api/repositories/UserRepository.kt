package com.seminario.api.repositories

import com.seminario.api.models.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: JpaRepository<User, Long> {
    @Query("select u from User u join fetch u.roles r where u.username = :username")
    fun findByUsername( username: String ): Optional<User>
}