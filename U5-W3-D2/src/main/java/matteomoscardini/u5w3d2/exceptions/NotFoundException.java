package matteomoscardini.u5w3d2.exceptions;

public class NotFoundException extends RuntimeException{
    public NotFoundException(int id){
        super("The searched Record with id " + id +"is not found");
    }
    public NotFoundException(String message){super(message);}

}
