package com.seminario.api.services

import com.seminario.api.utils.JwtUtil
import com.seminario.api.repositories.RoleRepository
import com.seminario.api.repositories.UserRepository
import com.seminario.api.models.User
import com.seminario.api.models.Role
import com.seminario.api.exceptions.AlreadyExists
import com.seminario.api.exceptions.Database
import com.seminario.api.exceptions.NotFound
import com.seminario.api.utils.Constants
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.lang.Exception
import java.util.*

@Service
class UserService : UserDetailsService {

    @Autowired
    val userRepository: UserRepository? = null

    @Autowired
    val roleRepository: RoleRepository? = null

    @Autowired
    private val jwtUtil: JwtUtil? = null

    private val idRoleDefault: Long = 2

    /**
     * Load a user by username
     * @param username: String
     * @throws NotFound
     * @return User
     */
    @Throws(NotFound::class)
    override fun loadUserByUsername(username: String): User {
        val user: Optional<User> = userByUsername(username)
        if (!user.isPresent) {
            throw NotFound(Constants.MESSAGE_USER_NOT_FOUND)
        } else {
            return user.get()
        }
    }

    /**
     * Load a user by id
     * @param id: Long
     * @throws NotFound
     * @return User
     */
    @Throws(NotFound::class)
    fun loadUserById(id: Long): User {
        val user: Optional<User> = userById(id)
        if (!user.isPresent) {
            throw NotFound(Constants.MESSAGE_USER_NOT_FOUND)
        } else {
            return user.get()
        }
    }

    /**
     * Create a new user
     * @param user: User
     * @throws AlreadyExists
     * @throws Database
     */
    @Throws(Database::class, AlreadyExists::class)
    fun save(user: User) {
        val userVerify: Optional<User> = userByUsername(user.username)
        if (userVerify.isPresent) {
            throw AlreadyExists(Constants.MESSAGE_USERNAME_EXISTS)
        } else {
            try {
                //default user role
                val role: Role = roleRepository!!.findById(idRoleDefault).get()
                user.roles = listOf(role)
                userRepository!!.save(user)
            } catch (e: Exception) {
                throw Database(e.message)
            }
        }
    }

    /**
     * Update a new user
     * @param user: User
     * @throws AlreadyExists
     * @throws Database
     */
    @Throws(Database::class, AlreadyExists::class)
    fun update(user: User) {
        try {
            userRepository!!.save(user)
        } catch (e: Exception) {
            throw Database(e.message)
        }
    }


    /**
     * Display user information by token
     * @param token: String
     * @throws NotFound
     * @return User
     */
    @Throws(NotFound::class)
    fun getProfile(token: String): User {
        val jwt: String = token.substring(7)
        val username: String = jwtUtil!!.extractUsername(jwt)
        val user: Optional<User> = userByUsername(username)
        if (user.isPresent) {
            return user.get()
        } else {
            throw NotFound(Constants.MESSAGE_USER_NOT_FOUND)
        }
    }

    /**
     * Get user by username
     * @param username: String
     * @throws Database
     * @return Optional<User>
     */
    @Throws(Database::class)
    fun userByUsername(username: String): Optional<User> {
        val op: Optional<User>
        try {
            op = userRepository!!.findByUsername(username)
        } catch (e: Exception) {
            throw Database(e.message)
        }
        return op
    }

    /**
     * Get user by id
     * @param id: Long
     * @throws Database
     * @return Optional<User>
     */
    @Throws(Database::class)
    private fun userById(id: Long): Optional<User> {
        val op: Optional<User>
        try {
            op = userRepository!!.findById(id)
        } catch (e: Exception) {
            throw Database(e.message)
        }
        return op
    }
}