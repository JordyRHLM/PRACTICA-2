package com.universidad.repository;

import com.universidad.model.Docente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface DocenteRepository extends JpaRepository<Docente, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByNroEmpleado(String nroEmpleado);
    Docente findByNroEmpleado(String nroEmpleado);
    Docente findByEstado(String estado);
    
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Docente> findById(Long id);
}