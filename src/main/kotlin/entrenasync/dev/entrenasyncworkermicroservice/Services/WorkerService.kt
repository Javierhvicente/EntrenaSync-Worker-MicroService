package entrenasync.dev.entrenasyncworkermicroservice.Services

import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerCreateRequest
import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerResponse
import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerUpdateRequest
import entrenasync.dev.entrenasyncworkermicroservice.Exceptions.WorkerExceptions
import entrenasync.dev.entrenasyncworkermicroservice.Mappers.toResponse
import entrenasync.dev.entrenasyncworkermicroservice.Mappers.toWorker
import entrenasync.dev.entrenasyncworkermicroservice.Models.WorkerType
import entrenasync.dev.entrenasyncworkermicroservice.Repositories.IWorkerRepository
import entrenasync.dev.entrenasyncworkermicroservice.Repositories.IWorkerTypeRepository
import org.bson.types.ObjectId
import org.lighthousegames.logging.logging
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class WorkerService(
    private val workerRepository: IWorkerRepository,
    private val workerTypeRepository: IWorkerTypeRepository
): IWorkerService {
    private val logger = logging()
    override fun getWorkers(pageable: Pageable): Page<WorkerResponse> {
        logger.info { "Getting all workers" }
        val workers = workerRepository.findAll(pageable)
        return workers.map{worker ->worker.toResponse()}
    }

    override fun getWorkerById(id: ObjectId): WorkerResponse? {
        logger.info { "Getting worker by $id" }
        return workerRepository.findById(id).orElseThrow{WorkerExceptions.WorkerNotFound(id.toString())}.toResponse()
    }

    override fun getWorkerByName(name: String): WorkerResponse? {
        logger.info { "Getting worker by name: $name" }
        return workerRepository.findByFullName(name)?.toResponse() ?: throw WorkerExceptions.WorkerNotFoundWithName(name)
    }

    override fun saveWorker(worker: WorkerCreateRequest): WorkerResponse {
        logger.info { "Saving worker" }
        val workerType = workerTypeRepository.findByName(worker.workerType)
            ?: workerTypeRepository.save(WorkerType(name = worker.workerType))

        val newWorker = worker.toWorker(workerType.id!!)

        return workerRepository.save(newWorker).toResponse()
    }

    override fun updateWorker(id: ObjectId, worker: WorkerUpdateRequest): WorkerResponse? {
        logger.info { "Updating worker with id: $id" }
        val oldWorker = workerRepository.findById(id).orElseThrow{WorkerExceptions.WorkerNotFound(id.toString())}
        val workerType = workerTypeRepository.findByName(worker.workerType)
            ?: workerTypeRepository.save(WorkerType(name = worker.workerType))
        val updatedWorker = worker.toWorker(oldWorker, workerType.id!!)
        return workerRepository.save(updatedWorker).toResponse()
    }

    override fun deleteWorker(id: ObjectId) {
        logger.info { "Deleting worker with id: $id" }
        return workerRepository.deleteById(id)
    }
}