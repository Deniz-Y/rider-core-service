package com.freenow.rider_core_service.service

import com.freenow.rider_core_service.domain.Rider
import com.freenow.rider_core_service.repository.RiderRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

@Service
class RiderService(private val riderRepository: RiderRepository) {

    private val passwordEncoder = BCryptPasswordEncoder()

    fun register(email: String, rawPassword: String, fName: String, lName: String): Rider {
        if (riderRepository.findByEmail(email) != null) throw RuntimeException("The email address is already registered.")

        val hashed = passwordEncoder.encode(rawPassword)
        val rider = Rider(email = email, passwordHash = hashed, firstName = fName, lastName = lName)
        return riderRepository.save(rider)
    }

    fun login(email: String, rawPassword: String): String {
        val rider = riderRepository.findByEmail(email) ?: throw RuntimeException("User not found!")

        return if (passwordEncoder.matches(rawPassword, rider.passwordHash)) {
            "Login successful!"
        } else {
            throw RuntimeException("Incorrect Password")
        }
    }

    fun updateRider(id: Long, newFirstName: String, newLastName: String): Rider {

        val existingRider = riderRepository.findById(id)
            .orElseThrow { RuntimeException("No user (ID: $id) was found to update!") }

        val updatedRider = existingRider.copy(
            firstName = newFirstName,
            lastName = newLastName
        )

        return riderRepository.save(updatedRider)
    }

    fun deleteRider(id: Long) {
        if (!riderRepository.existsById(id)) {
            throw RuntimeException("No user (ID: $id) was found to delete!")
        }
        riderRepository.deleteById(id)
    }
}