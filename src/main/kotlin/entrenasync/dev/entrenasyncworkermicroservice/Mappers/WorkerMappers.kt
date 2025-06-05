package entrenasync.dev.entrenasyncworkermicroservice.Mappers

import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerCreateRequest
import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerResponse
import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerUpdateRequest
import entrenasync.dev.entrenasyncworkermicroservice.Models.Worker
import entrenasync.dev.entrenasyncworkermicroservice.Models.WorkerType
import org.bson.types.ObjectId
import java.time.LocalDateTime

fun Worker.toResponse(): WorkerResponse {
    return WorkerResponse(
        id = id.toString(),
        id_user = id_user,
        id_workerType = id_workerType.toHexString(),
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

fun WorkerUpdateRequest.toWorker(oldWorker: Worker,workerTypeId: ObjectId ): Worker {
    return Worker(
        id = oldWorker.id,
        id_user = oldWorker.id_user,
        id_workerType = workerTypeId,
        phone = if (this.phone != null) this.phone else oldWorker.phone,
        fullName = this.fullName,
        address = this.address,
        gender = oldWorker.gender,
        birthdate = oldWorker.birthdate,
        avatar = oldWorker.avatar,
        service_list = oldWorker.service_list,
        createdAt = oldWorker.createdAt,
        degree_image = oldWorker.degree_image,
        updatedAt = LocalDateTime.now(),
    )
}

fun WorkerType.toResponse(): entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerTypeResponse {
    return entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerTypeResponse(
        id = id?.toHexString(),
        name = name
    )
}



