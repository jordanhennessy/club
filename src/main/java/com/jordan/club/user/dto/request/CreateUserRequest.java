package com.jordan.club.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = "The field 'name' must not be blank")
    private String name;

    @NotBlank(message = "The 'email' field must not be blank")
    @Email(message = "The 'email' field should be a valid email format")
    private String email;

}
