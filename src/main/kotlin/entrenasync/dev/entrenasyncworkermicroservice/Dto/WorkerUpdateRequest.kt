package entrenasync.dev.entrenasyncworkermicroservice.Dto

import entrenasync.dev.entrenasyncworkermicroservice.Models.Worker
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

class WorkerUpdateRequest(
    @field:Size(min = 3, max = 40, message = "Worker full name must be between 3 and 40 characters")
    val fullName: String?,

    @field:Size(min = 5, max = 100, message = "Worker address must be between 5 and 100 characters")
    val address: String?,

    val gender: Worker.Gender?,

    @field:Size(min = 9, max = 15, message = "Worker phone must be between 9 and 15 characters")
    var phone: String?,

    val workerType: String?,

    val avatar: String?,

) {

}