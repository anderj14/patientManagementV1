package PatientAPI.Dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDto {
    private int id;
    private String name;
    private String personId;
    private LocalDate DOB;
    private String gender;
    private String address;
    private long phone;
    private String email;
    private long noMedicare;
}
