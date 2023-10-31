package br.com.leonardo.authserviceapi.controllers.impl;

import br.com.leonardo.authserviceapi.controllers.AuthController;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticateResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthControllerImpl implements AuthController {
    @Override
    public ResponseEntity<AuthenticateResponse> authenticate(AuthenticateRequest request) {
        return ResponseEntity.ok().body(AuthenticateResponse.builder()
                        .type("success")
                        .token("User authenticated")
                .build());
    }
}
