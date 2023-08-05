package PatientAPI.Controllers;

import PatientAPI.Dto.PatientDto;
import PatientAPI.Dto.PatientResponse;
import PatientAPI.Models.Patient;
import PatientAPI.Service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/")
public class PatientController {

    private PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("patient")
    public ResponseEntity<PatientResponse> getPatient(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize
    ){
        return new ResponseEntity<>(patientService.getAllPatient(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("patient/{id}")
    public ResponseEntity<PatientDto> patientDetail(@PathVariable int id){
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PostMapping("patient/create")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<PatientDto> createPatient(@RequestBody PatientDto patientDto){
        return new ResponseEntity<>(patientService.createPatient(patientDto), HttpStatus.CREATED);
    }

    @PutMapping("pokemon/{id}/update")
    public ResponseEntity<PatientDto> updatePokemon(
            @RequestBody PatientDto patientDto,
            @PathVariable("id") int patientId){
       PatientDto response = patientService.updatePatient(patientDto, patientId);

       return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("patient/{id}/delete")
    public ResponseEntity<String> deletePatient(
            @PathVariable("id") int patientId){
        patientService.deletePatient(patientId);
        return new ResponseEntity<>("Patient delete", HttpStatus.OK);
    }
}
