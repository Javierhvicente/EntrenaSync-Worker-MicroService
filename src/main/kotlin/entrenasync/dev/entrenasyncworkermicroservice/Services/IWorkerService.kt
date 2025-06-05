package entrenasync.dev.entrenasyncworkermicroservice.Services

import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerCreateRequest
import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerResponse
import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerTypeResponse
import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerUpdateRequest
import entrenasync.dev.entrenasyncworkermicroservice.Models.WorkerType
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface IWorkerService {
    fun getWorkers(pageable: Pageable) : Page<WorkerResponse>
    fun getWorkerById(id: ObjectId) : WorkerResponse
    fun getWorkerByName(name: String) : WorkerResponse
    fun saveWorker(worker: WorkerCreateRequest) : WorkerResponse
    fun updateWorker(id: ObjectId, worker: WorkerUpdateRequest) : WorkerResponse
    fun deleteWorker(id: ObjectId)
    fun getWorkerTypeId(id: String): WorkerTypeResponse

    //TODO PATCH FOR IMAGES
}