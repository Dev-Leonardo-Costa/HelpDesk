package br.com.leonardo.service;

import br.com.leonardo.mapper.UserMapper;
import br.com.leonardo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    public UserResponse findById(final String id) {
        return userMapper.fromEntity(userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Object not found. Id: " + id + ", Type: " + UserResponse.class.getSimpleName()
                )));
    }


    public void save(CreateUserRequest createUserRequest) {
        verifyEmailAlreadyExists(createUserRequest.email(), null);
        userRepository.save(userMapper.fromRequest(createUserRequest));
    }

    private void verifyEmailAlreadyExists(final String email, final String id) {
        userRepository.findByEmail(email)
                .filter(user -> !user.getId().equals(id))
                .ifPresent(user -> {
                    throw new DataIntegrityViolationException("Email [" + email + "] already exists. Id: " + user.getId() + ", Type: " + UserResponse.class.getSimpleName());
                });
    }

    public List<UserResponse> findAll() {
        return userRepository.findAll()
                .stream().map(userMapper::fromEntity)
                .toList();
    }
}
