package io.rainett.deliverybackend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "user_email_uq", columnNames = "email")
})
public class UserBase {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Email(message = "Email format is invalid")
    @NotBlank(message = "Email should not be empty")
    @Size(max = 100, message = "Email size is out of range")
    @Column(nullable = false)
    private String email;

    @NotBlank(message = "Password should not be empty")
    @Size(min = 5, max = 100, message = "Password should be in range of 5 to 100 characters")
    @Column(nullable = false)
    private String password;

    @NotBlank(message = "First name should not be empty")
    @Size(min = 2, max = 100, message = "First name should be in range of 2 to 100 characters")
    @Column(nullable = false)
    private String firstName;

    @Size(min = 2, max = 100, message = "Last name should be in range of 2 to 100 characters")
    private String lastName;

}
