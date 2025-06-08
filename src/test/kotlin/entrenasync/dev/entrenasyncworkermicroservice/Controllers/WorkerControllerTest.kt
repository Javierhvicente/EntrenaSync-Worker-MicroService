package entrenasync.dev.entrenasyncworkermicroservice.Controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerCreateRequest
import entrenasync.dev.entrenasyncworkermicroservice.Dto.WorkerUpdateRequest
import entrenasync.dev.entrenasyncworkermicroservice.Mappers.toResponse
import entrenasync.dev.entrenasyncworkermicroservice.Mappers.toWorker
import entrenasync.dev.entrenasyncworkermicroservice.Models.Worker
import entrenasync.dev.entrenasyncworkermicroservice.Models.WorkerType
import entrenasync.dev.entrenasyncworkermicroservice.Services.IWorkerService
import io.mockk.every
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest
class WorkerControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockkBean
    private lateinit var workerService: IWorkerService

    private val sampleId = ObjectId.get()
    private val sampleCreateRequest = WorkerCreateRequest(
        id_user = "sampleUserId",
        fullName = "Sample Name",
        address = "Sample Address",
        avatar = "sampleAvatarUrl",
        birthdate = "1990-01-01",
        gender = Worker.Gender.Masculino.toString(),
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
    fun getAllWorkers() {
        val pageable = PageRequest.of(0, 10)
        val page = PageImpl(listOf(sampleResponse), pageable, 1)
        every { workerService.getWorkers(pageable) } returns page

        mockMvc.perform(
            get("/workers")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.content[0].id").value(sampleResponse.id))
            .andExpect(jsonPath("$.content[0].fullName").value(sampleResponse.fullName))
            .andExpect(jsonPath("$.totalElements").value(1))
    }

    @Test
    fun getWorkerById() {
        every { workerService.getWorkerById(sampleId) } returns sampleResponse

        mockMvc.perform(
            get("/workers/${sampleId}")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(sampleResponse.id))
            .andExpect(jsonPath("$.fullName").value(sampleResponse.fullName))
    }

    @Test
    fun searchWorkerByName() {
        val name = "Sample Name"
        every { workerService.getWorkerByName(name) } returns sampleResponse

        mockMvc.perform(
            get("/workers/name/${name}")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(sampleResponse.id))
            .andExpect(jsonPath("$.fullName").value(sampleResponse.fullName))
    }

    @Test
    fun createWorker() {
        every { workerService.saveWorker(sampleCreateRequest) } returns sampleResponse

        mockMvc.perform(
            MockMvcRequestBuilders.post("/workers")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(sampleCreateRequest))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(sampleResponse.id))
            .andExpect(jsonPath("$.fullName").value(sampleResponse.fullName))
    }

    @Test
    fun updateWorker() {
        val existingTypeId = ObjectId.get()
        val updatedRequest = WorkerUpdateRequest(
            fullName = "Sample Name UpdatedName",
            address = "Sample Address UpdatedAddress",
            avatar = "sampleAvatarUrlUpdated",
            gender = Worker.Gender.Masculino,
            phone = "1234567890",
            workerType = "sampleWorkerType",
        )
        val updatedResponse = updatedRequest.toWorker(sampleEntity, existingTypeId).toResponse()

        every { workerService.updateWorker(any(), any()) } returns updatedResponse

        mockMvc.perform(
            MockMvcRequestBuilders.put("/workers/${sampleId}")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(updatedRequest))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(updatedResponse.id))
            .andExpect(jsonPath("$.fullName").value(updatedResponse.fullName))
    }

    @Test
    fun deleteWorker() {
        every { workerService.deleteWorker(sampleId) } returns Unit

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/workers/${sampleId}")
        )
            .andExpect(status().isNoContent)
    }
}