package hospital.exceptions;
import java.time.LocalDate;
import java.time.LocalTime;
public class AppointmentConflictException extends Exception {

    public AppointmentConflictException(int doctorId, LocalDate date, LocalTime time) {
        super("Doctor [" + doctorId + "] is already booked on "
              + date + " at " + time + ". Please choose another slot.");
    }

    public AppointmentConflictException(String message) {
        super(message);
    }
}