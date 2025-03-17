package entrenasync.dev.entrenasyncworkermicroservice.Exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

sealed class WorkerExceptions(message: String): Exception(message) {
    class WorkerNotFound(id: String): WorkerExceptions("Worker not found with id: $id")
    class WorkerNotFoundWithName(name: String): WorkerExceptions("Worker not found with name: $name")
}