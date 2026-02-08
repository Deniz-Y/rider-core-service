package com.freenow.rider_core_service.repository


import com.freenow.rider_core_service.domain.Rider
import org.springframework.data.jpa.repository.JpaRepository

interface RiderRepository : JpaRepository<Rider, Long> {
    fun findByEmail(email: String): Rider?
}