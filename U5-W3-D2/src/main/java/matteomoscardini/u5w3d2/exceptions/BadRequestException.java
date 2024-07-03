package matteomoscardini.u5w3d2.exceptions;

import lombok.Getter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
public class BadRequestException extends RuntimeException{

    public List<ObjectError> errorsList;
    public BadRequestException(String message){
        super(message);
    }

    public BadRequestException(List<ObjectError> errorsList){
        super("Ci sono stati errori di validazione nel payload!");
        this.errorsList = errorsList;
    }


}
