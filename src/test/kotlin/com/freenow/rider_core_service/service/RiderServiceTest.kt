package com.freenow.rider_core_service.service

import com.freenow.rider_core_service.domain.Rider
import com.freenow.rider_core_service.repository.RiderRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.*
import java.util.*

class RiderServiceTest {

    private val riderRepository: RiderRepository = mock(RiderRepository::class.java)
    private val riderService = RiderService(riderRepository)

    @Test
    fun `should register a new rider successfully`() {
        // GIVEN
        val email = "deniz@test.com"
        `when`(riderRepository.findByEmail(email)).thenReturn(null)
        val savedRider = Rider(id = 1, email = email, passwordHash = "hashed_pass", firstName = "Deniz", lastName = "Yilmaz")
        `when`(riderRepository.save(any())).thenReturn(savedRider)

        // WHEN
        val result = riderService.register(email, "password123", "Deniz", "Yilmaz")

        // THEN
        assertNotNull(result)
        assertEquals(email, result.email)
        verify(riderRepository, times(1)).save(any())
    }

    @Test
    fun `should throw exception when email already exists during registration`() {
        val email = "existing@test.com"
        val existingRider = Rider(email = email, passwordHash = "hash", firstName = "A", lastName = "B")
        `when`(riderRepository.findByEmail(email)).thenReturn(existingRider)

        val exception = assertThrows(RuntimeException::class.java) {
            riderService.register(email, "pass", "Deniz", "Yilmaz")
        }
        assertEquals("The email address is already registered.", exception.message)
    }

    @Test
    fun `should update rider profile successfully`() {
        val riderId = 1L
        val existingRider = Rider(id = riderId, email = "deniz@test.com", passwordHash = "hash", firstName = "Old", lastName = "Name")
        `when`(riderRepository.findById(riderId)).thenReturn(Optional.of(existingRider))
        `when`(riderRepository.save(any())).thenReturn(existingRider.copy(firstName = "New", lastName = "Updated"))

        val result = riderService.updateRider(riderId, "New", "Updated")

        assertEquals("New", result.firstName)
        assertEquals("Updated", result.lastName)
        verify(riderRepository).save(any())
    }

    @Test
    fun `should throw exception when updating non-existent rider`() {
        // GIVEN
        val riderId = 99L
        `when`(riderRepository.findById(riderId)).thenReturn(Optional.empty())

        // WHEN & THEN
        val exception = assertThrows(RuntimeException::class.java) {
            riderService.updateRider(riderId, "New", "Name")
        }
        assertEquals("No user (ID: $riderId) was found to update!", exception.message)
    }

    @Test
    fun `should delete rider successfully`() {
        // GIVEN
        val riderId = 1L
        `when`(riderRepository.existsById(riderId)).thenReturn(true)

        // WHEN
        riderService.deleteRider(riderId)

        // THEN
        verify(riderRepository, times(1)).deleteById(riderId)
    }

    @Test
    fun `should throw exception when deleting non-existent rider`() {
        // GIVEN
        val riderId = 99L
        `when`(riderRepository.existsById(riderId)).thenReturn(false)

        // WHEN & THEN
        val exception = assertThrows(RuntimeException::class.java) {
            riderService.deleteRider(riderId)
        }
        assertEquals("No user (ID: $riderId) was found to delete!", exception.message)
    }
}