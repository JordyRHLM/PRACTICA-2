package com.universidad.validation;

import com.universidad.dto.DocenteDTO;
import com.universidad.repository.DocenteRepository;
import org.springframework.stereotype.Component;

@Component
public class DocenteValidator {

    private final DocenteRepository docenteRepository;

    public DocenteValidator(DocenteRepository docenteRepository) {
        this.docenteRepository = docenteRepository;
    }

    public void validarDocente(DocenteDTO docenteDTO) {
        validarCamposObligatorios(docenteDTO);
        validarUnicidadEmail(docenteDTO);
        validarUnicidadNroEmpleado(docenteDTO);
    }

    private void validarCamposObligatorios(DocenteDTO docenteDTO) {
        if (docenteDTO.getNombre() == null || docenteDTO.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del docente es obligatorio");
        }
        if (docenteDTO.getApellido() == null || docenteDTO.getApellido().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido del docente es obligatorio");
        }
        if (docenteDTO.getEmail() == null || docenteDTO.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email del docente es obligatorio");
        }
        if (docenteDTO.getNroEmpleado() == null || docenteDTO.getNroEmpleado().trim().isEmpty()) {
            throw new IllegalArgumentException("El número de empleado es obligatorio");
        }
        if (docenteDTO.getDepartamento() == null || docenteDTO.getDepartamento().trim().isEmpty()) {
            throw new IllegalArgumentException("El departamento es obligatorio");
        }
    }

    private void validarUnicidadEmail(DocenteDTO docenteDTO) {
        if (docenteRepository.existsByEmail(docenteDTO.getEmail())) {
            throw new IllegalArgumentException("Ya existe un docente con este email");
        }
    }

    private void validarUnicidadNroEmpleado(DocenteDTO docenteDTO) {
        if (docenteRepository.existsByNroEmpleado(docenteDTO.getNroEmpleado())) {
            throw new IllegalArgumentException("Ya existe un docente con este número de empleado");
        }
    }
}