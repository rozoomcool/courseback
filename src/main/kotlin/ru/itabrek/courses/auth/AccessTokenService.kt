package ru.itabrek.courses.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import ru.itabrek.courses.entity.User
import java.util.*
import java.util.function.Function
import javax.crypto.SecretKey


@Service
class AccessTokenService {
    @Value("\${token.access}")
    private val jwtSigningKey: String? = null

    fun extractUsername(token: String): String {
        return extractClaim(token) { obj: Claims -> obj.subject }
    }

    fun generateToken(userDetails: UserDetails): String {
        val claims: MutableMap<String, Any?> = HashMap()
        if (userDetails is User) {
            claims["username"] = userDetails.username
            claims["role"] = userDetails.role
            claims["authorities"] = userDetails.authorities
        }
        return generateToken(claims, userDetails)
    }

    fun isTokenValid(token: String, userDetails: UserDetails): Boolean {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    private fun <T> extractClaim(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    private fun isTokenExpired(token: String): Boolean {
        return extractExpiration(token).before(Date())
    }

    private fun extractExpiration(token: String): Date {
        return extractClaim(token) { obj: Claims -> obj.expiration }
    }

    private fun generateToken(extraClaims: Map<String, Any?>, userDetails: UserDetails): String {
        return Jwts.builder()
            .claims(extraClaims)
            .subject(userDetails.username)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + 1000 * 60 * 24))
            .signWith(signingKey, Jwts.SIG.HS256).compact()
    }

    private fun extractAllClaims(token: String): Claims {
        return Jwts.parser().verifyWith(signingKey).build().parseSignedClaims(token)
            .payload
    }

    private val signingKey: SecretKey
        private get() = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSigningKey))
}
