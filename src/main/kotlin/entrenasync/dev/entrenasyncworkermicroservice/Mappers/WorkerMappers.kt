package entrenasync.dev.entrenasyncworkermicroservice.Mappers

import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerCreateRequest
import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerResponse
import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerUpdateRequest
import entrenasync.dev.entrenasyncworkermicroservice.Models.Worker
import org.bson.types.ObjectId
import java.time.LocalDateTime

fun Worker.toResponse(): WorkerResponse {
    return WorkerResponse(
        id = id.toString(),
        id_user = id_user,
        id_workerType = id_workerType,
        fullName = fullName,
        birthdate = birthdate,
        phone = phone,
        address = address,
        gender = gender,
        degree_image = degree_image ?: "undefined",
        avatar = avatar ?: "undefinedAvatar_w8za89",
        service_list = service_list,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}

fun WorkerCreateRequest.toWorker(workerTypeId: ObjectId): Worker {
    return Worker(
        id = ObjectId.get(),
        id_user = id_user,
        fullName = fullName,
        birthdate = birthdate,
        phone = phone,
        address = address,
        gender = Worker.Gender.valueOf(gender),
        degree_image = degree_image,
        avatar = avatar,
        id_workerType = workerTypeId
    )
}

fun WorkerUpdateRequest.toWorker(oldWorker: Worker,workerTypeId: ObjectId? ): Worker {
    return Worker(
        id = oldWorker.id,
        id_user = oldWorker.id_user,
        id_workerType = workerTypeId ?: oldWorker.id_workerType,
        phone = if (this.phone != null) this.phone else oldWorker.phone,
        fullName = this.fullName ?: oldWorker.fullName,
        address = this.address ?: oldWorker.address,
        gender = this.gender ?: oldWorker.gender,
        birthdate = oldWorker.birthdate,
        avatar = this.avatar ?: oldWorker.avatar,
        service_list = oldWorker.service_list,
        createdAt = oldWorker.createdAt,
        degree_image = oldWorker.degree_image,
        updatedAt = LocalDateTime.now(),
    )
}



