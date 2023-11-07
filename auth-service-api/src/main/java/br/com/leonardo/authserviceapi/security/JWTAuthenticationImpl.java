package br.com.leonardo.authserviceapi.security;

import br.com.leonardo.authserviceapi.security.dtos.UserDetailsDTO;
import br.com.leonardo.authserviceapi.utils.JWTUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import models.requests.AuthenticateRequest;
import models.responses.AuthenticateResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Log4j2
@RequiredArgsConstructor
public class JWTAuthenticationImpl {

    private final JWTUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthenticateResponse authenticate(final AuthenticateRequest request) {
        try {
            log.info("Authenticating user: {}", request.email());
            final var authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
            return buildAuthenticateResponse((UserDetailsDTO) authentication.getPrincipal());

        } catch (BadCredentialsException ex) {
            log.error("Error on authenticate user: {}", request.email());
            throw new BadCredentialsException("Email or password invalid");
        }
    }

    /**
     * Metodo que constroi o objeto AuthenticateResponse
     * @param dto
     * @return
     */
    protected AuthenticateResponse buildAuthenticateResponse(final UserDetailsDTO dto) {
        log.info("Successfully authenticated user: {}", dto.getUsername());
        final  var token = jwtUtils.generateToken(dto);
        return AuthenticateResponse.builder()
                .type("Bearer")
                .token(token)
                .build();
    }
}
