package com.universidad.model;

import java.time.LocalDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad que representa la inscripción de un estudiante a una materia.
 * Incluye la relación con el estudiante, la materia y la fecha de inscripción.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "inscripcion")
public class Inscripcion {

    /**
     * Identificador único de la inscripción. Generado automáticamente.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Estudiante que realiza la inscripción.
     * No puede ser nulo.
     */
    @NotNull
    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    /**
     * Materia a la que se inscribe el estudiante.
     * No puede ser nulo.
     * Si la materia es eliminada, también se eliminan las inscripciones asociadas.
     */
    @NotNull
    @ManyToOne /*(cascade = CascadeType.REMOVE)*/
    @JoinColumn(name = "materia_id")
    private Materia materia;

    /**
     * Fecha en la que se realizó la inscripción.
     * No puede ser nula.
     */
    @NotNull
    private LocalDate fechaInscripcion;
}
