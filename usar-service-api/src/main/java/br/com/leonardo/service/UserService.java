package br.com.leonardo.service;

import br.com.leonardo.entity.User;
import br.com.leonardo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(final String id){
        return userRepository
                .findById(id).orElse(null);
    }


}
