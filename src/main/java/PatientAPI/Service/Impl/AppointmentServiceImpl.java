package PatientAPI.Service.Impl;

import PatientAPI.Dto.AppointmentDto;
import PatientAPI.Exceptions.AppointmentNotFoundException;
import PatientAPI.Exceptions.PatientNotFoundException;
import PatientAPI.Models.Appointment;
import PatientAPI.Models.Patient;
import PatientAPI.Repository.AppointmentRepository;
import PatientAPI.Repository.PatientRepository;
import PatientAPI.Service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl  implements AppointmentService {

    private AppointmentRepository appointmentRepository;
    private PatientRepository patientRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, PatientRepository patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    @Override
    public AppointmentDto createAppointment(int patientId, AppointmentDto appointmentDto) {
        Appointment appointment = mapToEntity(appointmentDto);
        Patient patient = patientRepository.findById(patientId).orElseThrow(() ->
                new PatientNotFoundException("Patient with associated appointment not found"));

        appointment.setPatient(patient);
        Appointment newAppointment = appointmentRepository.save(appointment);

        return mapToDto(newAppointment);
    }

    @Override
    public List<AppointmentDto> getAppointmentByPatientId(int id) {
        List<Appointment> appointments = appointmentRepository.findByPatientId(id);
        return appointments.stream().map(appointment -> mapToDto(appointment)).collect(Collectors.toList());
    }

    @Override
    public List<AppointmentDto> appointmentById(int patientId, int appointmentId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() ->
                new PatientNotFoundException("Patient not found"));

        List<Appointment> appointments = appointmentRepository.findByPatientId(patientId);

        List<AppointmentDto> appointmentDtos = new ArrayList<>();

        for (Appointment appointment : appointments) {
            if (appointment.getId() == appointmentId) {
                appointmentDtos.add(mapToDto(appointment));
            }
        }

        return appointmentDtos;
    }


    @Override
    public AppointmentDto updateAppointment(int patientId, int appointmentId, AppointmentDto appointmentDto) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() ->
                new PatientNotFoundException("Patient with associated appointment not found"));

        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() ->
                new AppointmentNotFoundException("Appointment with associated appointment not found"));

        if(appointment.getPatient().getId() != patient.getId()){
            throw new AppointmentNotFoundException("This appointment does not belong to a patient");
        }

        appointment.setDate(appointmentDto.getDate());
        appointment.setDescription(appointmentDto.getDescription());
        appointment.setTime(appointmentDto.getTime());

        Appointment updateAppointment = appointmentRepository.save(appointment);
        return mapToDto(updateAppointment);
    }

    @Override
    public void deleteAppointment(int patientId, int appointmentId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() ->
                new PatientNotFoundException("Patient with associated appointment not found"));

        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() ->
                new AppointmentNotFoundException("Appointment with associated appointment not found"));

        if(appointment.getPatient().getId() != patient.getId()){
            throw new AppointmentNotFoundException("This appointment does not belong to a patient");
        }

        appointmentRepository.delete(appointment);
    }

    private AppointmentDto mapToDto(Appointment appointment){
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setId(appointment.getId());
        appointmentDto.setDate(appointment.getDate());
        appointmentDto.setTime(appointment.getTime());
        appointmentDto.setDescription(appointment.getDescription());

        return appointmentDto;
    }

    private Appointment mapToEntity(AppointmentDto appointmentDto){
        Appointment appointment = new Appointment();
        appointment.setId(appointmentDto.getId());
        appointment.setDate(appointmentDto.getDate());
        appointment.setTime(appointmentDto.getTime());
        appointment.setDescription(appointmentDto.getDescription());

        return appointment;
    }
}
