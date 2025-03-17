package entrenasync.dev.entrenasyncworkermicroservice.Repositories

import entrenasync.dev.entrenasyncworkermicroservice.Models.Worker
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface IWorkerRepository: MongoRepository<Worker, ObjectId> {
    fun findByUsername(username: String): Worker?
}