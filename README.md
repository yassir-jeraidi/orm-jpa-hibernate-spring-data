
# ORM JPA Hibernate Spring Data Project

This project demonstrates the implementation of Object-Relational Mapping (ORM) using Spring Data JPA, Hibernate, and Spring Boot.

## Project Overview

This is a Spring Boot application that manages patient data using Spring Data JPA with both MySQL and H2 database support.

### Technology Stack

- Java 17
- Spring Boot 3.4.3
- Spring Data JPA
- Hibernate
- MySQL/H2 Database
- Lombok
- Maven

## Project Structure

### Entity Layer

The project includes a Patient entity with the following attributes:

```java
@Entity
@Table
@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private Date dateNaissance;
    private boolean malade;
    private int score;
}
```

### Repository Layer

The project uses Spring Data JPA repositories:

```java
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByNom(String nom);
    List<Patient> findByNomLike(String nom);
}
```

Custom finder methods:
- `findByNom`: Finds patients by exact name match
- `findByNomLike`: Finds patients by partial name match

### Test Data 

```java

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
```

- The `run` method is executed when the application starts.
- It creates two patients and saves them to the database.
- It then retrieves the list of patients and prints them.
- It demonstrates the use of custom finder methods.
- It updates a patient's name and deletes a patient.
- It deletes the patient with ID 1.
- The output is printed to the console.

## Configuration

### Database Configuration

The application supports both MySQL and H2 databases. The default configuration uses MySQL:

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/patients?createDatabaseIfNotExist=true
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: root
    password: root

  jpa:
    database-platform: org.hibernate.dialect.MySQL8Dialect
    hibernate:
      ddl-auto: update
```

H2 configuration (commented out):
```yaml
spring:
  datasource:
    url: jdbc:h2:mem:patients
    driver-class-name: org.h2.Driver
  jpa:
    database-platform: org.hibernate.dialect.H2Dialect
```

## Project Dependencies

Key dependencies include:

1. Spring Boot Starters:
   - spring-boot-starter-data-jpa
   - spring-boot-starter-web

2. Database Connectors:
   - mysql-connector-j
   - h2 database

3. Development Tools:
   - spring-boot-devtools
   - lombok (version 1.18.36)

## Building and Running

### Prerequisites

- JDK 17 or later
- Maven 3.x
- H2 Database (if using H2)
- MySQL Server (if using MySQL)

### Build Commands

Using Maven Wrapper:

```bash
./mvnw clean install
```

### Run Application

```bash
./mvnw spring-boot:run
```

## Development Features

1. **Hot Reload**: The project includes spring-boot-devtools for automatic application restart during development.

2. **Lombok Integration**: Reduces boilerplate code using Lombok annotations:
   - `@Getter` `@Setter`: Automatic getters and setters
   - `@Builder`: Builder pattern implementation
   - `@NoArgsConstructor` `@AllArgsConstructor`: Constructor generation

3. **JPA Features**:
   - Automatic table creation/updates
   - Built-in CRUD operations
   - Custom query methods
   - Transaction management

