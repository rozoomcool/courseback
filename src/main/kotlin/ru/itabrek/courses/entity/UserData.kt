package ru.itabrek.courses.entity

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.util.*

@Entity
@Table(name = "user_data")
class UserData(
    @Id
    val id: Long? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    @MapsId
    @JoinColumn(name = "id")
    var user: User? = null,

    @Column(name = "firstname")
    var firstname: String? = null,

    @Column(name = "lastname")
    var lastname: String? = null,

    @Column(name = "date_of_birth")
    var dateOfBirth: Date? = null,

    @Column(name = "phone")
    var phone: String? = null,

    @Column(name = "created_at", nullable = false, updatable = false) @CreationTimestamp
    var createdAt: Date? = null,

    @Column(name = "updated_at", nullable = false) @UpdateTimestamp
    var updatedAt: Date? = null
)