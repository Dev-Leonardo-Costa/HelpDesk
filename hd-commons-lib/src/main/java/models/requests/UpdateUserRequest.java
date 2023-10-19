package models.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.With;
import models.enums.ProfileEnum;

import java.util.Set;

@With
public record UpdateUserRequest(

        @Schema(description = "User name", example = "Leonardo Costa")
        @Size(min = 3, max = 50, message = "Name must contain between 3 and 50 characters")
        String name,

        @Schema(description = "User email", example = "exemplo@gmail.com")
        @Email(message = "Invalid email")
        @Size(min = 6, max = 50, message = "Email must contain between 6 and 50 characters")
        String email,
        @Schema(description = "User password", example = "leo123#")
        @Size(min = 6, max = 50, message = "Email must contain between 6 and 50 characters")
        String password,

        @Schema(description = "User profiles", example = "[\"ROLE_ADMIN\",\"ROLE_CUSTOMER\"]")
        Set<ProfileEnum> profiles

) {}
