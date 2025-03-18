package entrenasync.dev.entrenasyncworkermicroservice.Repositories

import entrenasync.dev.entrenasyncworkermicroservice.Models.WorkerType
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface IWorkerTypeRepository : MongoRepository<WorkerType, ObjectId> {
    fun findByName(name: String): WorkerType?
}