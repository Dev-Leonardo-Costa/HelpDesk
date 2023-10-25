package br.com.leonardo.service;

import br.com.leonardo.entity.User;
import br.com.leonardo.mapper.UserMapper;
import br.com.leonardo.repository.UserRepository;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static br.com.leonardo.creator.CreatorUtils.genarateMock;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository repository;
    @Mock
    private UserMapper mapper;
    @Mock
    private BCryptPasswordEncoder encoder;


    @Test
    void whenCallFindByIdWithValidIdThenReturnUserResponse() {

        when(repository.findById(anyString())).thenReturn(Optional.of(new User()));
        when(mapper.fromEntity(any(User.class))).thenReturn(mock(UserResponse.class));

        final var response = service.findById("1");

        assertNotNull(response);
        assertEquals(UserResponse.class, response.getClass());

        verify(repository, times(1)).findById(anyString());
        verify(mapper, times(1)).fromEntity(any(User.class));

    }

    @Test
    void whenCallFindByIdWithInvalidIdThenThrowResourceNotFoundException() {
        when(repository.findById(anyString())).thenReturn(Optional.empty());

        try {
            service.findById("1");
        } catch (Exception e) {
            assertEquals(ResourceNotFoundException.class, e.getClass());
            assertEquals("Object not found. Id: 1, Type: UserResponse", e.getMessage());
        }

        verify(repository, times(1)).findById(anyString());
        verify(mapper, never()).fromEntity(any(User.class));
    }

    @Test
    void whenFindAllThenReturnListUserResponse() {
        when(repository.findAll()).thenReturn(List.of(new User(), new User()));
        when(mapper.fromEntity(any(User.class))).thenReturn(genarateMock(UserResponse.class));

        final var response = service.findAll();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals(UserResponse.class, response.get(0).getClass());

        verify(repository, times(1)).findAll();
        verify(mapper, times(2)).fromEntity(any(User.class));
    }

    @Test
    void whenCallSaveThenReturnSuccess() {
        final var request = genarateMock(CreateUserRequest.class);

        when(mapper.fromRequest(any())).thenReturn(new User());
        when(encoder.encode(anyString())).thenReturn("encoded");
        when(repository.save(any(User.class))).thenReturn(new User());
        when(repository.findByEmail(anyString())).thenReturn(Optional.empty());

        service.save(request);

        verify(mapper).fromRequest(request);
        verify(encoder).encode(request.password());
        verify(repository).save(any(User.class));
        verify(repository).findByEmail(request.email());


    }

    @Test
    void update() {
    }
}