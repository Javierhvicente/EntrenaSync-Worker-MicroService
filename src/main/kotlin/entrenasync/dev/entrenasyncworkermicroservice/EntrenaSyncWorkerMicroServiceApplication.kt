package entrenasync.dev.entrenasyncworkermicroservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EntrenaSyncWorkerMicroServiceApplication

fun main(args: Array<String>) {
    runApplication<EntrenaSyncWorkerMicroServiceApplication>(*args)
}
