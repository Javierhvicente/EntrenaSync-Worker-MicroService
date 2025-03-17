package entrenasync.dev.entrenasyncworkermicroservice.Services

import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerCreateRequest
import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerResponse
import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerUpdateRequest
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface IWorkerService {
    fun getWorkers(pageable: Pageable) : Page<WorkerResponse>
    fun getWorkerById(id: ObjectId) : WorkerResponse?
    fun getWorkerByName(name: String) : WorkerResponse?
    fun saveWorker(worker: WorkerCreateRequest) : WorkerResponse
    fun updateWorker(id: ObjectId, worker: WorkerUpdateRequest) : WorkerResponse?
    fun deleteWorker(id: ObjectId)

    //TODO PATCH FOR IMAGES
}