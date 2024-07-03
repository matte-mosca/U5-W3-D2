package matteomoscardini.u5w3d2.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record EmployeeLogin(@NotEmpty(message = "Don't forget to add a password")
                            @Size(min = 3, max = 30, message = "The password must be between 3 and 30 characters")
                            String password,


                            @NotEmpty(message = "Don't forget to add your email")
                            @Email(message = "The given email is not valid")
                            String eMail){
}
