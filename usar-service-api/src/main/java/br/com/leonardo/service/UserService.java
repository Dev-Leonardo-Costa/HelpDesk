package br.com.leonardo.service;

import br.com.leonardo.mapper.UserMapper;
import br.com.leonardo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.exceptions.ResourceNotFoundException;
import models.requests.CreateUserRequest;
import models.responses.UserResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper mapper;

    public UserResponse findById(final String id){
        return mapper.fromEntity(userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Object not found. Id: " + id +", Type: " + UserResponse.class.getSimpleName()
        )));
    }


    public void save(CreateUserRequest createUserRequest) {
        userRepository.save(mapper.fromRequest(createUserRequest));
    }
}
