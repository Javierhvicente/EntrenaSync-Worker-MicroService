package entrenasync.dev.entrenasyncworkermicroservice.Services

import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerCreateRequest
import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerUpdateRequest
import entrenasync.dev.entrenasyncworkermicroservice.Exceptions.WorkerExceptions
import entrenasync.dev.entrenasyncworkermicroservice.Mappers.toResponse
import entrenasync.dev.entrenasyncworkermicroservice.Mappers.toWorker
import entrenasync.dev.entrenasyncworkermicroservice.Models.Worker
import entrenasync.dev.entrenasyncworkermicroservice.Models.WorkerType
import entrenasync.dev.entrenasyncworkermicroservice.Repositories.IWorkerRepository
import entrenasync.dev.entrenasyncworkermicroservice.Repositories.IWorkerTypeRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.util.*

@ExtendWith(MockKExtension::class)
class WorkerServiceTest {

    @MockK
    lateinit var workerRepository: IWorkerRepository

    @MockK
    lateinit var workerTypeRepository: IWorkerTypeRepository

    @InjectMockKs
    lateinit var service: WorkerService

    private val sampleId = ObjectId.get()
    private val sampleCreateRequest = WorkerCreateRequest(
        id_user = "sampleUserId",
        fullName = "Sample Name",
        address = "Sample Address",
        avatar = "sampleAvatarUrl",
        birthdate = "1990-01-01",
        gender = Worker.Gender.MALE.toString(),
        phone = "1234567890",
        workerType = "sampleWorkerType",
        degree_image = "sampleDegreeImageUrl",
    )

    private val sampleEntity by lazy {
        sampleCreateRequest.toWorker(ObjectId.get()).apply {
            id = sampleId
        }
    }

    private val sampleResponse by lazy { sampleEntity.toResponse() }


    @Test
    fun getWorkers() {

        val pageable = PageRequest.of(0, 5)
        val page = PageImpl(listOf(sampleEntity), pageable, 1)
        every { workerRepository.findAll(pageable) } returns page

        val result = service.getWorkers(pageable)
        assertEquals(1, result.totalElements)
        assertEquals(sampleResponse, result.content.first())
        every { workerRepository.findAll(pageable) } returns page
    }

    @Test
    fun getWorkerById() {
        every { workerRepository.findById(sampleId) } returns Optional.of(sampleEntity)

        val result = service.getWorkerById(sampleId)

        assertNotNull(result)
        assertEquals(sampleResponse, result)
        every { workerRepository.findById(sampleId) }
    }

    @Test
    fun getWorkerByIdNotFound() {
        every { workerRepository.findById(sampleId) } returns Optional.empty()

        assertThrows(WorkerExceptions.WorkerNotFound::class.java) {
            service.getWorkerById(sampleId)
        }
    }

    @Test
    fun getWorkerByName() {
        val workerName = "Sample Name"
        every { workerRepository.findByFullName(workerName) } returns sampleEntity

        val result = service.getWorkerByName(workerName)

        assertNotNull(result)
        assertEquals(sampleResponse, result)
        every { workerRepository.findByFullName(workerName) }
    }

    @Test
    fun getWorkerByNameNotFound() {
        val workerName = "Sample Name"
        every { workerRepository.findByFullName(workerName) } returns null

        assertThrows(WorkerExceptions.WorkerNotFoundWithName::class.java) {
            service.getWorkerByName(workerName)
        }
    }

    @Test
    fun saveWorker() {
        val workerTypeId = ObjectId.get()
        every { workerTypeRepository.findByName(sampleCreateRequest.workerType) } returns null
        every { workerTypeRepository.save(any()) } returns WorkerType(name = sampleCreateRequest.workerType, id = workerTypeId)

        every { workerRepository.save(any()) } returns sampleEntity

        val result = service.saveWorker(sampleCreateRequest)

        assertNotNull(result)
        assertEquals(sampleResponse, result)

        verify {
            workerTypeRepository.findByName(sampleCreateRequest.workerType)
            workerTypeRepository.save(ofType(WorkerType::class))
            workerRepository.save(ofType(Worker::class))
        }
    }


    @Test
    fun `updateWorker con tipo existente actualiza correctamente`() {
        // 1) Creamos un WorkerType que ya existe
        val existingTypeId = ObjectId.get()
        val existingType = WorkerType(id = existingTypeId, name = "sampleWorkerType")

        // 2) Preparamos un request de actualización
        val updateRequest = WorkerUpdateRequest(
            fullName = "Sample Name UpdatedName",
            address = "Sample Address UpdatedAddress",
            birthdate = "1990-01-01",
            gender = Worker.Gender.MALE,
            phone = "1234567890",
            workerType = "sampleWorkerType",
        )

        // 3) Stub findById y findByName para rutas “existentes”
        every { workerRepository.findById(sampleId) } returns Optional.of(sampleEntity)
        every { workerTypeRepository.findByName("sampleWorkerType") } returns existingType
        every { workerRepository.save(any()) } returns updateRequest.toWorker(sampleEntity, existingTypeId).apply {
            id = sampleId
            updatedAt = sampleEntity.updatedAt // Simulamos que la fecha de actualización es la misma
        }

        // 5) Invocamos updateWorker
        val response = service.updateWorker(sampleId, updateRequest)

        assertEquals(sampleId.toHexString(), response.id)
        assertEquals(updateRequest.fullName, response.fullName)
        assertEquals(updateRequest.address, response.address)
        assertEquals(sampleEntity.birthdate, response.birthdate)

        // 7) Verificamos interacción con repos
        verify { workerRepository.findById(any()) }
        verify { workerTypeRepository.findByName(any()) }
        verify { workerRepository.save(any()) }
    }

    @Test
    fun `updateWorker con tipo no existente actualiza correctamente`() {
        // 1) Creamos un WorkerType que ya existe
        val nonExistingTypeId = ObjectId.get()
        val nonExistingType = WorkerType(id = nonExistingTypeId, name = "nonExistingWorkerType")

        // 2) Preparamos un request de actualización
        val updateRequest = WorkerUpdateRequest(
            fullName = "Sample Name UpdatedName",
            address = "Sample Address UpdatedAddress",
            birthdate = "1990-01-01",
            gender = Worker.Gender.MALE,
            phone = "1234567890",
            workerType = "nonExistingWorkerType",
        )

        // 3) Stub findById y findByName para rutas “existentes”
        every { workerRepository.findById(sampleId) } returns Optional.of(sampleEntity)
        every { workerTypeRepository.findByName(any()) } returns null
        every { workerTypeRepository.save(any()) } returns nonExistingType
        every { workerRepository.save(any()) } returns updateRequest.toWorker(sampleEntity, nonExistingTypeId).apply {
            id = sampleId
            updatedAt = sampleEntity.updatedAt // Simulamos que la fecha de actualización es la misma
        }

        // 5) Invocamos updateWorker
        val response = service.updateWorker(sampleId, updateRequest)

        assertEquals(sampleId.toHexString(), response.id)
        assertEquals(updateRequest.fullName, response.fullName)
        assertEquals(updateRequest.address, response.address)
        assertEquals(sampleEntity.birthdate, response.birthdate)

        // 7) Verificamos interacción con repos
        verify { workerRepository.findById(any()) }
        verify { workerTypeRepository.findByName(any()) }
        verify { workerRepository.save(any()) }
    }

    @Test
    fun `updateWorker not found`() {

        val updateRequest = WorkerUpdateRequest(
            fullName = "Sample Name UpdatedName",
            address = "Sample Address UpdatedAddress",
            birthdate = "1990-01-01",
            gender = Worker.Gender.MALE,
            phone = "1234567890",
            workerType = "nonExistingWorkerType",
        )

        every { workerRepository.findById(any()) } returns Optional.empty()

        assertThrows(WorkerExceptions.WorkerNotFound::class.java) {
            service.updateWorker(sampleId, updateRequest)
        }
    }

    @Test
    fun deleteWorker() {
        every { workerRepository.deleteById(sampleId) } returns Unit

        service.deleteWorker(sampleId)

        every { workerRepository.deleteById(sampleId) }
    }
}