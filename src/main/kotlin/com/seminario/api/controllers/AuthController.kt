package com.seminario.api.controllers

import com.seminario.api.dto.LoginRequest
import com.seminario.api.dto.LoginResponse
import com.seminario.api.dto.UserDTO
import com.seminario.api.exceptions.BadCredentials
import com.seminario.api.models.User
import com.seminario.api.services.UserService
import com.seminario.api.utils.Constants
import com.seminario.api.utils.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(Constants.URL_BASE_AUTH)
class AuthController {

    @Autowired
    private val authManager: AuthenticationManager? = null

    @Autowired
    private val userService: UserService? = null

    @Autowired
    private val jwtTokenUtil: JwtUtil? = null

    /**
     * Display the token access.
     * @param loginRequest: LoginRequest
     * @return ResponseEntity<AuthResponse>
     */
    @PostMapping("/token")
    fun authenticate(@RequestBody loginRequest: LoginRequest): ResponseEntity<LoginResponse> {
        try {
            authManager!!.authenticate(
                    UsernamePasswordAuthenticationToken(
                            loginRequest.username,
                            loginRequest.password
                    )
            )
        } catch (e: Exception) {
            throw BadCredentials(
                    Constants.MESSAGE_BAD_CREDENTIALS
            )
        }

        val userDetails: User = userService!!
                .loadUserByUsername(loginRequest.username)
        val accessToken: String? = jwtTokenUtil!!
                .generateToken(userDetails)

        return ResponseEntity(
                LoginResponse(
                        accessToken = accessToken!!,
                        role = userDetails.roles?.get(0)?.name!!,
                        scopes = userDetails.getScopes()
                ),
                HttpStatus.OK
        )
    }

    /**
     * Create a new user.
     * @param user: UserDTO
     * @return ResponseEntity
     */
    @PostMapping("/signup")
    fun signup(@RequestBody user: UserDTO): ResponseEntity<Any> {
        val initials: String = user.name.substring(0, 1)
        user.picture = "https://i1.wp.com/cdn.auth0.com/avatars/$initials.png"
        userService!!.save(User(
                name = user.name,
                username = user.username,
                password = hashPassword(user.password!!),
                picture = user.picture
        ))
        return ResponseEntity(HttpStatus.CREATED)
    }

    /**
     * Get a user information.
     * @param jwt: String
     * @return ResponseEntity<UserDTO>
     */
    @GetMapping("/profile")
    fun profile( @RequestHeader("Authorization") jwt: String ): ResponseEntity<UserDTO> {
        val user: User = userService!!.getProfile(jwt)
        return ResponseEntity(UserDTO(
                id = user.id,
                name = user.name,
                username = user.username,
                picture = user.picture,
                password = null
        ), HttpStatus.OK)
    }

    /**
     * Encrypt the password
     * @param password: String
     * @return String
     */
    private fun hashPassword(password: String): String {
        val encoder = BCryptPasswordEncoder()
        return encoder.encode(password)
    }
}
