package ru.itabrek.courses.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.itabrek.courses.model.Role
import java.util.Date

@Entity
@Table(name = "users")
class User(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "username", unique = true, nullable = false) @JvmField
    var username: String,

    @JsonIgnore @Column(name = "password", nullable = false) @JvmField
    var password: String,

    @Column(name = "role", nullable = false) @Enumerated(value = EnumType.STRING)
    var role: Role,

    @Column(name = "created_at", nullable = false, updatable = false) @CreationTimestamp
    var createdAt: Date? = null,

    @Column(name = "updated_at", nullable = false) @UpdateTimestamp
    var updatedAt: Date? = null
): UserDetails {
    @JsonIgnore
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return mutableListOf(SimpleGrantedAuthority(role.name))
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}