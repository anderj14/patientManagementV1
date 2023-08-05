package PatientAPI.Service;

import PatientAPI.Dto.AppointmentDto;
import PatientAPI.Models.Patient;

import java.util.List;
public interface AppointmentService {
    AppointmentDto createAppointment(int patientId, AppointmentDto appointmentDto);
    List<AppointmentDto> getAppointmentByPatientId(int id);
    List<AppointmentDto> appointmentById(int appointmentId, int patientId);
    AppointmentDto updateAppointment(int patientId, int appointmentId, AppointmentDto appointmentDto);

    void deleteAppointment(int patientId, int appointmentId);
}
