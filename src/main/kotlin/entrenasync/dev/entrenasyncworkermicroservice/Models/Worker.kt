package entrenasync.dev.entrenasyncworkermicroservice.Models

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document("Workers")
@TypeAlias("Workers")
class Worker (
    @Id
    val id: ObjectId?,

    @field:NotBlank(message = "User id must be not empty")
    val id_user: String, //TODO Puede dar problemas, posibilidad de cambiarlo por string

    @field:Min(value = 3, message = "Minimum value for worker full name must be 3")
    @field:Max(value =40, message = "Maximum value for worker full name must be 40")
    @field:NotBlank(message = "Worker full name must not be empty")
    val fullName: String,

    @field:NotBlank(message = "Worker address must")
    @field:Min(value =5, message = "Minimum value for worker address must be 5")
    @field:Max(value = 100, message = "Maximum value for worker address must be 100")
    val address: String,

    val avatar: String = "METER URL DEFAULT",

    @field:NotBlank(message = "Worker birth date must not be empty")
    val birthdate: String,

    @field:NotBlank(message = "Worker gender must be not empty")
    val gender: Gender,

    @field:Min(value = 9, message = "Minimum value for worker phone is 9")
    @field:Max(value = 15, message = "Max value for worker phone is 15")
    @field:NotBlank(message = "Worker phone must not be empty")
    var phone: String?,

    @field:NotBlank(message = "Worker Type id must be not empty")
    val id_workerType: ObjectId,

    @field:NotBlank(message = "The degree image of the worker must be not empty")
    val degree_image: String,

    val service_list: List<String> = emptyList(),

    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now()

) {
    @JsonProperty
    fun get_id(): String?{
        return id?.toHexString()
    }

    enum class Gender{
        MALE,
        FEMALE,
    }
}