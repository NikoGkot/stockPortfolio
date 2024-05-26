package com.kotlinspring.intg.controller.security

//import com.kotlinspring.dto.LoginDTO
import com.kotlinspring.repository.RoleRepository
import com.kotlinspring.repository.UserRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
class AuthControllerTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var roleRepository: RoleRepository

    @BeforeEach
    fun setUp() {
        userRepository.deleteAll()
        roleRepository.deleteAll()
    }

    @Test
    fun `register user successfully`() {
        val registerDTO = RegisterDTO(username = "testuser", password = "password")

        webTestClient.post()
            .uri("/auth/register")
            .bodyValue(registerDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(String::class.java)
            .consumeWith { response ->
                assertEquals("User registered successfully", response.responseBody)
            }
    }

//    @Test
//    fun `login user successfully`() {
//        val registerDTO = RegisterDTO(username = "testuser", password = "password")
//        webTestClient.post()
//            .uri("/auth/register")
//            .bodyValue(registerDTO)
//            .exchange()
//            .expectStatus().isCreated
//
//        val loginDTO = LoginDTO(username = "testuser", password = "password")
//        webTestClient.post()
//            .uri("/auth/login")
//            .bodyValue(loginDTO)
//            .exchange()
//            .expectStatus().isOk
//            .expectBody(String::class.java)
//            .consumeWith { response ->
//                assertEquals("User logged in successfully", response.responseBody)
//            }
//    }
}
