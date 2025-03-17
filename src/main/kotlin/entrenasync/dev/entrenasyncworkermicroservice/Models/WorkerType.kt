package entrenasync.dev.entrenasyncworkermicroservice.Models

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.TypeAlias
import org.springframework.data.mongodb.core.mapping.Document

@Document("WorkerType")
@TypeAlias("WorkerType")
class WorkerType(
    @Id
    var id: ObjectId? = null,
    @field:Min(value = 3, message = "Minimum value for worker type full name must be 3")
    @field:Max(value =20, message = "Maximum value for worker type full name must be 20")
    @field:NotBlank(message = "Worker type name must not be empty")
    var name: String
) {

}