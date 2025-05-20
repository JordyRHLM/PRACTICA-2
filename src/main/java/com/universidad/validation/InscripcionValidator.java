package com.universidad.validation;

import com.universidad.dto.InscripcionDTO;
import com.universidad.repository.EstudianteRepository;
import com.universidad.repository.InscripcionRepository;
import com.universidad.repository.MateriaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Componente de validación para inscripciones.
 * Realiza validaciones de negocio y de datos para la entidad Inscripcion.
 */
@Component
public class InscripcionValidator {

    private final InscripcionRepository inscripcionRepository;
    private final EstudianteRepository estudianteRepository;
    private final MateriaRepository materiaRepository;

    public InscripcionValidator(
            InscripcionRepository inscripcionRepository,
            EstudianteRepository estudianteRepository,
            MateriaRepository materiaRepository
    ) {
        this.inscripcionRepository = inscripcionRepository;
        this.estudianteRepository = estudianteRepository;
        this.materiaRepository = materiaRepository;
    }

    /**
     * Valida que los datos mínimos de la inscripción estén presentes.
     * Lanza IllegalArgumentException si algún dato es inválido.
     * @param inscripcion InscripcionDTO a validar.
     */
    public void validaDatosMinimos(InscripcionDTO inscripcion) {
        if (inscripcion == null
                || inscripcion.getEstudianteId() == null
                || inscripcion.getMateriaId() == null
                || inscripcion.getFechaInscripcion() == null) {
            throw new IllegalArgumentException("Datos mínimos de inscripción incompletos.");
        }
    }

    /**
     * Valida que la fecha de inscripción no sea futura.
     * Lanza IllegalArgumentException si la fecha es inválida.
     * @param inscripcion InscripcionDTO a validar.
     */
    public void validaFechaNoFutura(InscripcionDTO inscripcion) {
        if (inscripcion.getFechaInscripcion().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de inscripción no puede ser futura.");
        }
    }

    /**
     * Valida que el estudiante exista.
     * Lanza IllegalArgumentException si no existe.
     * @param estudianteId ID del estudiante.
     */
    public void validaEstudianteExistente(Long estudianteId) {
        if (!estudianteRepository.existsById(estudianteId)) {
            throw new IllegalArgumentException("El estudiante no existe.");
        }
    }

    /**
     * Valida que la materia exista.
     * Lanza IllegalArgumentException si no existe.
     * @param materiaId ID de la materia.
     */
    public void validaMateriaExistente(Long materiaId) {
        if (!materiaRepository.existsById(materiaId)) {
            throw new IllegalArgumentException("La materia no existe.");
        }
    }

    /**
     * Valida que no exista una inscripción duplicada para el mismo estudiante y materia.
     * Lanza IllegalArgumentException si ya existe.
     * @param estudianteId ID del estudiante.
     * @param materiaId ID de la materia.
     */
    public void validaNoInscripcionDuplicada(Long estudianteId, Long materiaId) {
        if (inscripcionRepository.existsByEstudianteIdAndMateriaId(estudianteId, materiaId)) {
            throw new IllegalArgumentException("El estudiante ya está inscrito en esta materia.");
        }
    }

    /**
     * Validación completa de inscripción.
     * Lanza excepción si alguna validación falla.
     * @param inscripcion InscripcionDTO a validar.
     */
    public void validacionCompletaInscripcion(InscripcionDTO inscripcion) {
        validaDatosMinimos(inscripcion);
        validaEstudianteExistente(inscripcion.getEstudianteId());
        validaMateriaExistente(inscripcion.getMateriaId());
        validaFechaNoFutura(inscripcion);
        validaNoInscripcionDuplicada(inscripcion.getEstudianteId(), inscripcion.getMateriaId());
    }

    /**
     * Excepción de negocio específica para inscripciones.
     */
    public static class BusinessException extends RuntimeException {
        public BusinessException(String message) {
            super(message);
        }
    }
}

