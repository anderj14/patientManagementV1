package PatientAPI.Exceptions;

public class PatientNotFoundException extends RuntimeException{
    private static final long serialVerisionUID = 1;

    public PatientNotFoundException(String message){
        super(message);
    }
}
