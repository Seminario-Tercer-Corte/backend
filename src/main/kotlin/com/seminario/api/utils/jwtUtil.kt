package com.seminario.api.utils

import com.seminario.api.models.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.*
import java.util.function.Function


@Service
class JwtUtil {

    @Value("\${jwt.secret}")
    private val secret: String = ""

    /**
     * Extract username from token
     * @param token: String?
     * @return String
     */
    fun extractUsername(token: String?): String {
        return extractClaim(token, Function { obj: Claims -> obj.subject })
    }

    /**
     * Extract date expiration from token
     * @param token: String?
     * @return Date
     */
    fun extractExpiration(token: String?): Date {
        return extractClaim(token, Function { obj: Claims -> obj.expiration })
    }

    /**
     * Extract a claim from token
     * @param token: String?
     * @param claimsResolver: Function<Claims, T>
     * @return T
     */
    fun <T> extractClaim(token: String?, claimsResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    /**
     * Extract claims from token
     * @param token: String?
     * @return Claims
     */
    private fun extractAllClaims(token: String?): Claims {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
    }

    /**
     * Validates if the token expired
     * @param token: String?
     * @return Boolean?
     */
    private fun isTokenExpired(token: String?): Boolean {
        return extractExpiration(token).before(Date())
    }

    /**
     * Generate token with the scopes user
     * @param userDetails: User
     * @return String?
     */
    fun generateToken(userDetails: User): String? {
        val claims: Map<String, Any> = hashMapOf(Pair("scope", userDetails.getScopes()))
        return createToken(claims, userDetails.username)
    }

    /**
     * Create the token
     * @param claims: Map<String, Any>
     * @param subject: String
     * @return String
     */
    private fun createToken(claims: Map<String, Any>, subject: String): String {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date(System.currentTimeMillis()))
                .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret).compact()
    }

    /**
     * Validate user and if the token expired
     * @param token: String?
     * @param userDetails: UserDetails
     * @return Boolean
     */
    fun validateToken(token: String?, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }
}