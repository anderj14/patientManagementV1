package PatientAPI.Controllers;

import PatientAPI.Dto.AppointmentDto;
import PatientAPI.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/")
public class AppointmentController {
    private AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping("/patient/{patientId}/appointment")
    public ResponseEntity<AppointmentDto> createAppointment(
            @PathVariable(value = "patientId") int patientId,
            @RequestBody AppointmentDto appointmentDto
    ){
        return new ResponseEntity<>(
                appointmentService.createAppointment(
                        patientId, appointmentDto), HttpStatus.CREATED);
    }

    @GetMapping("/patient/{patientId}/appointments")
    public List<AppointmentDto> getAppointmentByPatientId(
            @PathVariable(value = "patientId") int patientId
    ){
        return appointmentService.getAppointmentByPatientId(patientId);
    }

    @GetMapping("/patient/{patientId}/appointment/{id}")
    public ResponseEntity<List<AppointmentDto>> getAppointmentById(
            @PathVariable(value = "patientId") int patientId,
            @PathVariable(value = "id") int appointmentId
    ) {
        List<AppointmentDto> appointmentDtos = appointmentService.appointmentById(patientId, appointmentId);

        return new ResponseEntity<>(appointmentDtos, HttpStatus.OK);
    }


    @PutMapping("/patient/{patientId}/appointment/{id}")
    public ResponseEntity<AppointmentDto> updateAppointment(
            @PathVariable(value = "patientId") int patientId,
            @PathVariable(value = "id") int appointmentId,
            @RequestBody AppointmentDto appointmentDto
    ){
        AppointmentDto updateAppointment = appointmentService.updateAppointment(patientId, appointmentId, appointmentDto);

        return new ResponseEntity<>(updateAppointment, HttpStatus.OK);
    }

    @DeleteMapping("/patient/{patientId}/appointment/{id}")
    public ResponseEntity<String> deleteAppointment(
            @PathVariable(value = "patientId") int patientId,
            @PathVariable(value = "id") int appointmentId
    ){
        appointmentService.deleteAppointment(patientId, appointmentId);
        return new ResponseEntity<>("Appointment delete", HttpStatus.OK);
    }
}
