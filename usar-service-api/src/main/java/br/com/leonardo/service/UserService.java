package br.com.leonardo.service;

import br.com.leonardo.entity.User;
import br.com.leonardo.mapper.UserMapper;
import br.com.leonardo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import models.responses.UserResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper mapper;

    public UserResponse findById(final String id){
        return mapper.fromEntity(userRepository.findById(id).orElse(null));
    }


}
