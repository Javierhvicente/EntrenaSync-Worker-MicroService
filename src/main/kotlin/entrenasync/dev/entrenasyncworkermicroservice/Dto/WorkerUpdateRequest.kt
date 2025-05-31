package entrenasync.dev.entrenasyncworkermicroservice.Dto

import entrenasync.dev.entrenasyncworkermicroservice.Models.Worker
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class WorkerUpdateRequest(
    @field:Size(min = 3, max = 40, message = "Worker full name must be between 3 and 40 characters")
    @field:NotBlank(message = "Worker full name must not be empty")
    val fullName: String,

    @field:NotBlank(message = "Worker address must")
    @field:Size(min = 5, max = 100, message = "Worker address must be between 5 and 100 characters")
    val address: String,

    @field:NotBlank(message = "Worker birth date must not be empty")
    val birthdate: String,

    val gender: Worker.Gender,

    @field:Size(min = 9, max = 15, message = "Worker phone must be between 9 and 15 characters")
    @field:NotBlank(message = "Worker phone must not be empty")
    var phone: String?,

    @field:NotBlank(message = "Worker Type id must be not empty")
    val workerType: String,

) {

}