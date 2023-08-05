package PatientAPI.Exceptions;

public class AppointmentNotFoundException extends RuntimeException{
    private static final long serialVerisionUID = 2;

    public AppointmentNotFoundException(String message) {
        super(message);
    }
}
