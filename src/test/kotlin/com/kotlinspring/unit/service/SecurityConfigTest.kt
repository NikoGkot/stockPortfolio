package com.kotlinspring.unit.service

import com.kotlinspring.entity.UserEntity
import com.kotlinspring.repository.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated
import org.springframework.test.web.servlet.MockMvc

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var userRepository: UserRepository

    @BeforeEach
    fun setup() {
        userRepository.deleteAll()
        // Create a test user in the repository
        val user = UserEntity(
            username = "testuser",
            password = "{noop}testpass" // Use {noop} prefix to indicate no encoding
        )
        userRepository.save(user)
    }

    @Test
    fun `test login with appropriate user`() {
        mockMvc.perform(formLogin().user("testuser").password("testpass"))
            .andExpect(authenticated().withUsername("testuser"))
    }
}
