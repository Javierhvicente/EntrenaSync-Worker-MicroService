package entrenasync.dev.entrenasyncworkermicroservice.Controllers

import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerCreateRequest
import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerResponse
import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerUpdateRequest
import entrenasync.dev.entrenasyncworkermicroservice.Services.WorkerService
import jakarta.validation.Valid
import org.bson.types.ObjectId
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/workers")
class WorkerController(
    private val workerService: WorkerService
) {

    @GetMapping
    fun getAllWorkers():ResponseEntity<Page<WorkerResponse>>{
        return ResponseEntity.ok().body(workerService.getWorkers(PageRequest.of(0,10)))
    }

    @GetMapping("/{id}")
    fun getWorkerById(@PathVariable id: ObjectId): ResponseEntity<WorkerResponse>{
        return ResponseEntity.ok().body(workerService.getWorkerById(id))
    }

    @GetMapping("/name/{name}")
    fun searchWorkerByName(@PathVariable name: String): ResponseEntity<WorkerResponse>{
        return ResponseEntity.ok().body(workerService.getWorkerByName(name))
    }

    @PostMapping
    fun createWorker(@Valid @RequestBody workerRequest: WorkerCreateRequest): ResponseEntity<WorkerResponse>{
        return ResponseEntity.ok().body(workerService.saveWorker(workerRequest))
    }

    @PutMapping("/{id}")
    fun updateWorker(@PathVariable id: ObjectId, @Valid @RequestBody workerRequest: WorkerUpdateRequest): ResponseEntity<WorkerResponse>{
        return ResponseEntity.ok().body(workerService.updateWorker(id, workerRequest))
    }

    @DeleteMapping("/{id}")
    fun deleteWorker(@PathVariable id: ObjectId): ResponseEntity<Void>{
        workerService.deleteWorker(id)
        return ResponseEntity.noContent().build()
    }
}