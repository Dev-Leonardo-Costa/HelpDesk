package br.com.leonardo.controller.impl;

import br.com.leonardo.controller.UserController;
import br.com.leonardo.entity.User;
import br.com.leonardo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserControllerImpl implements UserController {

    private final UserService service;

    @Override
    public ResponseEntity<User> findById(String id) {
        return ResponseEntity.ok().body(service.findById(id));
    }


}
