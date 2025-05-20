package com.universidad.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO para la entidad Inscripcion.
 * Contiene los datos necesarios para transferir información sobre inscripciones entre capas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InscripcionDTO {
    /**
     * Identificador único de la inscripción.
     */
    private Long id;

    /**
     * ID del estudiante que realiza la inscripción.
     * No puede ser nulo.
     */
    @NotNull(message = "El ID del estudiante es obligatorio")
    private Long estudianteId;

    /**
     * ID de la materia a la que se inscribe el estudiante.
     * No puede ser nulo.
     */
    @NotNull(message = "El ID de la materia es obligatorio")
    private Long materiaId;

    /**
     * Fecha en la que se realizó la inscripción.
     * No puede ser nula.
     */
    @NotNull(message = "La fecha de inscripción es obligatoria")
    private LocalDate fechaInscripcion;
}
