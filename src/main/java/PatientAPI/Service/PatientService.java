package PatientAPI.Service;

import PatientAPI.Dto.PatientDto;
import PatientAPI.Dto.PatientResponse;

import java.util.List;

public interface PatientService {
    PatientDto createPatient(PatientDto patientDto);
    PatientResponse getAllPatient(int pageNo, int pageSize);
    PatientDto getPatientById(int id);
    PatientDto updatePatient(PatientDto patientDto, int id);
    void deletePatient(int id);
}
