package com.freenow.rider_core_service.controller

import com.freenow.rider_core_service.domain.Rider
import com.freenow.rider_core_service.service.RiderService
import org.springframework.web.bind.annotation.*

// for register and login, use them:
data class AuthRequest(
    val email: String,
    val password: String,
    val firstName: String? = null,
    val lastName: String? = null
)

// for update only names are required
data class UpdateProfileRequest(
    val firstName: String,
    val lastName: String
)

@RestController
@RequestMapping("/api/auth")
class RiderController(private val riderService: RiderService) {

    @PostMapping("/register")
    fun register(@RequestBody request: AuthRequest) =
        riderService.register(request.email, request.password, request.firstName!!, request.lastName!!)

    @PostMapping("/login")
    fun login(@RequestBody request: AuthRequest) =
        riderService.login(request.email, request.password)


    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: UpdateProfileRequest): Rider {
        return riderService.updateRider(id, request.firstName, request.lastName)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Long): String {
        riderService.deleteRider(id)
        return "The user with ID $id has been successfully removed from the system."
    }
}