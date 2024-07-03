package matteomoscardini.u5w3d2.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record PayloadEmployee(

        @NotEmpty(message = "Don't forget to add a username")
        @Size(min = 3, max = 30, message = "The username must be between 3 and 30 characters")
        String username,

        @NotEmpty(message = "Don't forget to add your first name")
        @Size(min = 3, max = 30, message = "The first must be between 3 and 30 characters")
        String name,

        @NotEmpty(message = "Don't forget to add your surname")
        @Size(min = 3, max = 30, message = "The surname must be between 3 and 30 characters")
        String surname,

        @NotEmpty(message = "Don't forget to add your email")
        @Email(message = "The given email is not valid")
        String eMail,
        @NotEmpty(message = "Don't forget to add a password")
        @Size(min = 3, max = 30, message = "The password must be between 3 and 30 characters")
        String password
        ) {


}
