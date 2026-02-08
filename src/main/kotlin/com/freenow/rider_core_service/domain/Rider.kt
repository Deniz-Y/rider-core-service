package com.freenow.rider_core_service.domain

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Entity
@Table(name = "riders")
data class Rider(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @field:Email(message = "Please enter a valid email address!")
    @Column(unique = true, nullable = false)
    val email: String,

    @Column(nullable = false)
    val passwordHash: String,

    @field:NotBlank(message = "The name cannot be empty.")
    val firstName: String,

    @field:NotBlank(message = "The surname cannot be empty.")
    val lastName: String
)