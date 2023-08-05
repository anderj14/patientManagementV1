package PatientAPI.Repository;

import PatientAPI.Models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    List<Appointment> findByPatientId(int patientId);
    Optional<Appointment> findByIdAndPatientId(int appointmentId, int patientId);

}
