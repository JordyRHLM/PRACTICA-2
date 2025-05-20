package com.universidad.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

/**
 * DTO para la entidad Materia.
 * Contiene los datos necesarios para la transferencia de información sobre materias entre capas.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MateriaDTO implements Serializable {

    /**
     * Identificador único de la materia.
     */
    private Long id;

    /**
     * Nombre de la materia.
     * No puede estar en blanco y tiene un máximo de 100 caracteres.
     */
    @NotBlank(message = "El nombre de la materia es obligatorio")
    @Size(max = 100, message = "El nombre no debe superar 100 caracteres")
    private String nombreMateria;

    /**
     * Código único de la materia.
     * No puede estar en blanco y tiene un máximo de 20 caracteres.
     */
    @NotBlank(message = "El código único es obligatorio")
    @Size(max = 20, message = "El código único no debe superar 20 caracteres")
    private String codigoUnico;

    /**
     * Número de créditos de la materia.
     * Debe estar entre 1 y 20.
     */
    @NotNull(message = "Los créditos son obligatorios")
    @Min(value = 1, message = "El mínimo de créditos es 1")
    @Max(value = 20, message = "El máximo de créditos es 20")
    private Integer creditos;

    /**
     * ID del docente asignado a la materia.
     * No puede ser nulo.
     */
    @NotNull(message = "El docente es obligatorio")
    private Long docenteId;

    /**
     * Lista de IDs de materias que son prerequisitos para esta materia.
     */
    private List<Long> prerequisitos;

    /**
     * Lista de IDs de materias para las que esta materia es prerequisito.
     */
    private List<Long> esPrerequisitoDe;
}