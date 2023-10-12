package io.rainett.deliverybackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserBaseDto {

    private Long id;

    @Email(message = "Email format is invalid")
    @NotBlank(message = "Email should not be empty")
    @Size(max = 100, message = "Email size is out of range")
    private String email;

    @NotBlank(message = "First name should not be empty")
    @Size(min = 2, max = 100, message = "First name should be in range of 2 to 100 characters")
    private String firstName;

    @Size(min = 2, max = 100, message = "Last name should be in range of 2 to 100 characters")
    private String lastName;

    private String token;


}
