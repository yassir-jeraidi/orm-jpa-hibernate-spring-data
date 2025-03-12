package com.orm.spring_data.repositories;

import com.orm.spring_data.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByNom(String nom);

    List<Patient> findByNomLike(String nom);
}