package entrenasync.dev.entrenasyncworkermicroservice.Dto

import entrenasync.dev.entrenasyncworkermicroservice.Models.Worker
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank

class WorkerUpdateRequest(
    @field:Min(value = 3, message = "Minimum value for worker full name must be 3")
    @field:Max(value =40, message = "Maximum value for worker full name must be 40")
    @field:NotBlank(message = "Worker full name must not be empty")
    val fullName: String,

    @field:NotBlank(message = "Worker address must")
    @field:Min(value =5, message = "Minimum value for worker address must be 5")
    @field:Max(value = 100, message = "Maximum value for worker address must be 100")
    val address: String,

    @field:NotBlank(message = "Worker birth date must not be empty")
    val birthdate: String,

    @field:NotBlank(message = "Worker gender must be not empty")
    val gender: Worker.Gender,

    @field:Min(value = 9, message = "Minimum value for worker phone is 9")
    @field:Max(value = 15, message = "Max value for worker phone is 15")
    @field:NotBlank(message = "Worker phone must not be empty")
    var phone: String?,

    @field:NotBlank(message = "Worker Type id must be not empty")
    val workerType: String,

) {

}