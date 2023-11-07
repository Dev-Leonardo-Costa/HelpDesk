package br.com.leonardo.authserviceapi.models;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Builder
@Getter
@Document
public class RefreshToken {

    @Id
    private String id;
    private String username;
    private LocalDateTime  cretedAt;
    private LocalDateTime expiresAt;

}
