package com.seminario.api

import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.seminario.api.dto.LoginRequest
import com.seminario.api.dto.OrganizationDTO
import com.seminario.api.dto.TeamDTO
import com.seminario.api.dto.UserDTO
import com.seminario.api.models.User
import com.seminario.api.utils.Constants
import com.seminario.api.utils.JwtUtil
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultMatcher
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import java.io.IOException

@SpringBootTest
class AuthTests {

    @Autowired
    private var mvc: MockMvc? = null

    @Autowired
    private val jwtTokenUtil: JwtUtil? = null

    @Autowired
    val webApplicationContext: WebApplicationContext? = null

    @BeforeEach
    fun setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext!!).build()
    }

    @Test
    fun authBadCredentials() {
        val credentials = LoginRequest(username = "invalid", password = "invalid")
        mvc!!.perform(
                MockMvcRequestBuilders
                        .post("${Constants.URL_BASE_AUTH}/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(credentials)!!)
        )
                .andExpect(
                        ResultMatcher.matchAll(
                                MockMvcResultMatchers.status().isForbidden
                        )
                )
    }

    @Test
    fun authSuccess() {
        val credentials = LoginRequest(username = "test123", password = "admin123")
        mvc!!.perform(
                MockMvcRequestBuilders
                        .post("${Constants.URL_BASE_AUTH}/token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(credentials)!!)
        )
                .andExpect(
                        ResultMatcher.matchAll(
                                MockMvcResultMatchers.status().isOk
                        )
                )
    }

    @Test
    fun signupSuccess() {
        val user = UserDTO(
                id = null,
                name = "Test",
                username = "test452",
                password = "user123",
                picture = null,
                job = "Lider",
                team = TeamDTO(
                        1,
                        "",
                        "",
                        "",
                        "",
                        "",
                        1
                )
        )
        mvc!!.perform(
                MockMvcRequestBuilders
                        .post("${Constants.URL_BASE_AUTH}/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(user)!!)
        )
                .andExpect(
                        ResultMatcher.matchAll(
                                MockMvcResultMatchers.status().isCreated
                        )
                )
    }

    @Test
    fun signupAlreadyExists() {
        val user = UserDTO(
                id = null,
                name = "Test",
                username = "test123",
                password = "user123",
                picture = null,
                job = "Lider",
                team = TeamDTO(
                        1,
                        "",
                        "",
                        "",
                        "",
                        "",
                        1
                )
        )
        mvc!!.perform(
                MockMvcRequestBuilders
                        .post("${Constants.URL_BASE_AUTH}/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapToJson(user)!!)
        )
                .andExpect(
                        ResultMatcher.matchAll(
                                MockMvcResultMatchers.status().isBadRequest
                        )
                )
    }

    /*@Test
    fun profile() {
        val token = jwtTokenUtil!!.generateToken(User(
                name = "Test",
                username = "test123",
                password = "admin123",
                picture = null,
                job = "Lider"
        ))

        mvc!!.perform(
                MockMvcRequestBuilders
                        .get("${Constants.URL_BASE_AUTH}/profile")
                        .header("Authorization", "Bearer $token")
        )
                .andExpect(
                        ResultMatcher.matchAll(
                                MockMvcResultMatchers.status().isOk
                        )
                )
    }*/

    @Test
    fun profileNotFound() {
        val token = jwtTokenUtil!!.generateToken(User(
                name = "test",
                username = "test12",
                password = "admin123",
                picture = null,
                job = "Lider"
        ))

        mvc!!.perform(
                MockMvcRequestBuilders
                        .get("${Constants.URL_BASE_AUTH}/profile")
                        .header("Authorization", "Bearer $token")
        )
                .andExpect(
                        ResultMatcher.matchAll(
                                MockMvcResultMatchers.status().isNotFound
                        )
                )
    }

    @Throws(JsonProcessingException::class)
    private fun mapToJson(obj: Any?): String? {
        val objectMapper = ObjectMapper()
        return objectMapper.writeValueAsString(obj)
    }

    @Throws(JsonParseException::class, JsonMappingException::class, IOException::class)
    private fun <T> mapFromJson(json: String?, clazz: Class<T>?): T {
        val objectMapper = ObjectMapper()
        return objectMapper.readValue(json, clazz)
    }

}
