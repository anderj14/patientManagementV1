package PatientAPI.Service.Impl;

import PatientAPI.Dto.PatientDto;
import PatientAPI.Dto.PatientResponse;
import PatientAPI.Exceptions.PatientNotFoundException;
import PatientAPI.Models.Patient;
import PatientAPI.Repository.PatientRepository;
import PatientAPI.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PatientServiceImpl implements PatientService {
    private PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public PatientDto createPatient(PatientDto patientDto) {
        Patient patient = new Patient();
        patient.setName(patientDto.getName());
        patient.setPersonId(patientDto.getPersonId());
        patient.setDOB(patientDto.getDOB());
        patient.setGender(patientDto.getGender());
        patient.setAddress(patientDto.getAddress());
        patient.setPhone(patientDto.getPhone());
        patient.setEmail(patientDto.getEmail());
        patient.setNoMedicare(patientDto.getNoMedicare());

        Patient newPatient = patientRepository.save(patient);

        PatientDto patientResponse = new PatientDto();
        patientResponse.setId(newPatient.getId());
        patientResponse.setName(newPatient.getName());
        patientResponse.setPersonId(newPatient.getPersonId());
        patientResponse.setDOB(newPatient.getDOB());
        patientResponse.setGender(newPatient.getGender());
        patientResponse.setAddress(newPatient.getAddress());
        patientResponse.setPhone(newPatient.getPhone());
        patientResponse.setEmail(newPatient.getEmail());
        patientResponse.setNoMedicare(newPatient.getNoMedicare());

        return patientResponse;
    }

    @Override
    public PatientResponse getAllPatient(int pageNo, int  pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Patient> patients = patientRepository.findAll(pageable);
        List<Patient> listOfPatient = patients.getContent();
        List<PatientDto> content = listOfPatient.stream().map(p ->mapToDto(p)).collect(Collectors.toList());

        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setContent(content);
        patientResponse.setPageNo(patients.getNumber());
        patientResponse.setPageSize(patients.getSize());
        patientResponse.setTotalElements(patients.getTotalElements());
        patientResponse.setTotalPages(patients.getTotalPages());
        patientResponse.setLast(patients.isLast());

        return patientResponse;
    }


    @Override
    public PatientDto getPatientById(int id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() ->
                new PatientNotFoundException("Patient could not be found"));

        return mapToDto(patient);
    }

    @Override
    public PatientDto updatePatient(PatientDto patientDto, int id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() ->
                new PatientNotFoundException("Patient could not be update"));

        patient.setName(patientDto.getName());
        patient.setPersonId(patientDto.getPersonId());
        patient.setDOB(patientDto.getDOB());
        patient.setGender(patientDto.getGender());
        patient.setAddress(patientDto.getAddress());
        patient.setPhone(patientDto.getPhone());
        patient.setEmail(patientDto.getEmail());
        patient.setNoMedicare(patientDto.getNoMedicare());

        Patient updatePatient = patientRepository.save(patient);
        return mapToDto(updatePatient);
    }

    @Override
    public void deletePatient(int id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() ->
                new PatientNotFoundException("Patient could no be delete"));
        patientRepository.delete(patient);

    }

    private PatientDto mapToDto(Patient patient){
        PatientDto patientDto = new PatientDto();
        patientDto.setId(patient.getId());
        patientDto.setName(patient.getName());
        patientDto.setPersonId(patient.getPersonId());
        patientDto.setDOB(patient.getDOB());
        patientDto.setGender(patient.getGender());
        patientDto.setAddress(patient.getAddress());
        patientDto.setPhone(patient.getPhone());
        patientDto.setEmail(patient.getEmail());
        patientDto.setNoMedicare(patient.getNoMedicare());

        return patientDto;
    }

    private Patient mapToEntity(PatientDto patientDto){
        Patient patient = new Patient();
        patient.setId(patientDto.getId());
        patient.setName(patientDto.getName());
        patient.setPersonId(patientDto.getPersonId());
        patient.setDOB(patientDto.getDOB());
        patient.setGender(patientDto.getGender());
        patient.setAddress(patientDto.getAddress());
        patient.setPhone(patientDto.getPhone());
        patient.setEmail(patientDto.getEmail());
        patient.setNoMedicare(patientDto.getNoMedicare());

        return patient;
    }

}
