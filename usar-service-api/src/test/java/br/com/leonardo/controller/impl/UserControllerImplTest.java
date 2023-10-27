package br.com.leonardo.controller.impl;

import br.com.leonardo.entity.User;
import br.com.leonardo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.requests.CreateUserRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static br.com.leonardo.creator.CreatorUtils.genarateMock;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerImplTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository repository;

    @Test
    @DisplayName("Find user by id sucess")
    void testFindByIdWithSuccess() throws Exception {

        final var entity = genarateMock(User.class);
        final var userId = repository.save(entity).getId();

        mockMvc.perform(get("/api/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId))
                .andExpect(jsonPath("$.name").value(entity.getName()))
                .andExpect(jsonPath("$.email").value(entity.getEmail()))
                .andExpect(jsonPath("$.password").value(entity.getPassword()))
                .andExpect(jsonPath("$.profiles").isArray());

        repository.deleteById(userId);
    }

    @Test
    @DisplayName("Find user by id not found")
    void testFindByIdWithNotFound() throws Exception {

        mockMvc.perform(get("/api/users/{id}", "123"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Object not found. Id: 123, Type: UserResponse"))
                .andExpect(jsonPath("$.error").value(NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.path").value("/api/users/123"))
                .andExpect(jsonPath("$.status").value(NOT_FOUND.value()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @DisplayName("Find all users sucess")
    void testFindAllWithSuccess() throws Exception {

        final var entity1 = genarateMock(User.class);
        final var entity2 = genarateMock(User.class);

        repository.saveAll(List.of(entity1, entity2));

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").isNotEmpty())
                .andExpect(jsonPath("$[1].name").isNotEmpty())
                .andExpect(jsonPath("$[0].profiles").isArray());

        repository.deleteAll(List.of(entity1, entity2));

    }

    @Test
    @DisplayName("Save new user sucess")
    void testSaveWithSuccess() throws Exception {
        final var validEmail = "test1234@gmail.com";
        final var request = genarateMock(CreateUserRequest.class).withEmail(validEmail);

        mockMvc.perform(post("/api/users")
                .contentType(APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(status().isCreated());

        repository.deleteByEmail(validEmail);
    }
    @Test
    @DisplayName("Save not user conflict email")
    void testSaveUserWithConflictEmail() throws Exception {
        final var validEmail = "test123@gmail.com";
        final var entity = genarateMock(User.class).withEmail(validEmail);

        repository.save(entity);

        final var request = genarateMock(CreateUserRequest.class).withEmail(validEmail);

        mockMvc.perform(post("/api/users")
                .contentType(APPLICATION_JSON)
                .content(toJson(request)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Email [" + validEmail + "] already exists."))
                .andExpect(jsonPath("$.error").value("Conflict"))
                .andExpect(jsonPath("$.path").value("/api/users"))
                .andExpect(jsonPath("$.status").value(CONFLICT.value()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());

        repository.deleteById(entity.getId());

    }

    private String toJson(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (final Exception e) {
            throw new RuntimeException("Error to convert object to json", e);
        }
    }

}