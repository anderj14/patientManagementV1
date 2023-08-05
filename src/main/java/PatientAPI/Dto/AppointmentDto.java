package PatientAPI.Dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AppointmentDto {
    private int id;
    private LocalDate date;
    private LocalTime time;
    private String description;
}
