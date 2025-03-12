package com.orm.spring_data;

import com.orm.spring_data.entities.Patient;
import com.orm.spring_data.repositories.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;
import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class OrmJpaHibernateSpringDataApplication implements CommandLineRunner {

    private final PatientRepository patientRepository;

    public static void main(String[] args) {
        SpringApplication.run(OrmJpaHibernateSpringDataApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Creating patients...");
        Patient patient1 = Patient.builder()
                .id(null)
                .nom("Patient 1")
                .dateNaissance(new Date())
                .malade(false)
                .score(10)
                .build();
        Patient patient2 = Patient.builder()
                .id(null)
                .nom("Patient 2")
                .dateNaissance(new Date())
                .malade(true)
                .score(20)
                .build();
        patientRepository.saveAll(List.of(patient1, patient2));
        System.out.println("Patients created.");
        System.out.println("*".repeat(30));

        System.out.println("List of patients:");
        List<Patient> patients = patientRepository.findAll();
        patients.forEach(p -> System.out.println(p.toString()));

        System.out.println("*".repeat(30));

        System.out.println("Find patient by id:");
        Patient patient = patientRepository.findById(1L).orElse(null);
        System.out.println(patient);

        System.out.println("*".repeat(30));
        System.out.println("Find patient by name:");
        List<Patient> patientsByName = patientRepository.findByNomLike("Patient");
        patientsByName.forEach(p -> System.out.println(p.toString()));

        System.out.println("*".repeat(30));
        System.out.println("Update patient:");
        Patient patientToUpdate = patientRepository.findById(1L).orElse(null);
        if (patientToUpdate != null) {
            patientToUpdate.setNom("Patient 1 Updated");
            patientRepository.save(patientToUpdate);
        }

        System.out.println("*".repeat(30));
        System.out.println("Delete patient:");
        patientRepository.deleteById(1L);
        System.out.println("Patient deleted.");
    }
}
