package entrenasync.dev.entrenasyncworkermicroservice.Dto

import entrenasync.dev.entrenasyncworkermicroservice.Models.Worker
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class WorkerCreateRequest(

    @field:NotBlank(message = "User id must be not empty")
    val id_user: String,

    @field:Size(min = 3, max = 40, message = "Worker full name must be between 3 and 40 characters")
    @field:NotBlank(message = "Worker full name must not be empty")
    val fullName: String,

    @field:Size(min = 5, max = 100, message = "Worker address must be between 5 and 100 characters")
    @field:NotBlank(message = "Worker address must not be empty")
    val address: String,

    val avatar: String = "METER URL DEFAULT",

    @field:NotBlank(message = "Worker birth date must not be empty")
    val birthdate: String,

    @field:NotBlank(message = "Worker gender must not be empty")
    val gender: String,

    @field:Size(min = 9, max = 15, message = "Worker phone must be between 9 and 15 characters")
    @field:NotBlank(message = "Worker phone must not be empty")
    var phone: String?,

    @field:NotBlank(message = "Worker Type id must not be empty")
    val workerType: String,

    @field:NotBlank(message = "The degree image of the worker must not be empty")
    val degree_image: String,
)
