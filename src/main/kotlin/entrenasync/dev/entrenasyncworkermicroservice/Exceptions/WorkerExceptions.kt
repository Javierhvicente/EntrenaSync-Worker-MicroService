package entrenasync.dev.entrenasyncworkermicroservice.Exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
@ResponseStatus(HttpStatus.BAD_REQUEST)
sealed class WorkerExceptions(message: String): Exception(message) {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    class WorkerNotFound(id: String): WorkerExceptions("Worker not found with id: $id")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    class WorkerNotFoundWithName(name: String): WorkerExceptions("Worker not found with name: $name")
}