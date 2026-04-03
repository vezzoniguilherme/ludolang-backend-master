package support

import org.testcontainers.containers.PostgreSQLContainer

object Containers {

    val POSTGRES: PostgreSQLContainer<*> =
        PostgreSQLContainer("postgres:16")

    init {
        POSTGRES.start()
    }
}