package com.universidad.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Entidad JPA que representa a un docente en el sistema universitario.
 * Hereda los atributos comunes de la clase Persona.
 * Incluye información de identificación, contacto, estado y control de auditoría.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "docente")
public class Docente extends Persona {

    /**
     * Número de empleado del docente, único en el sistema.
     */
    @Column(name = "nro_empleado", nullable = false, unique = true, length = 20)
    private String nroEmpleado;

    /**
     * Departamento al que pertenece el docente.
     */
    @Column(name = "departamento", nullable = false, length = 50)
    private String departamento;

    /**
     * Estado actual del docente (activo o inactivo).
     */
    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    /**
     * Usuario que dio de alta al docente.
     */
    @Column(name = "usuario_alta", nullable = false, length = 50)
    private String usuarioAlta;

    /**
     * Fecha en la que se dio de alta al docente.
     */
    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    /**
     * Usuario que modificó los datos del docente.
     */
    @Column(name = "usuario_modificacion", length = 50)
    private String usuarioModificacion;

    /**
     * Fecha en la que se modificó al docente.
     */
    @Column(name = "fecha_modificacion")
    private LocalDate fechaModificacion;

    /**
     * Usuario que dio de baja al docente.
     */
    @Column(name = "usuario_baja", length = 50)
    private String usuarioBaja;

    /**
     * Fecha en la que se dio de baja al docente.
     */
    @Column(name = "fecha_baja")
    private LocalDate fechaBaja;

    /**
     * Motivo de baja del docente (renuncia, jubilacion o despido).
     */
    @Column(name = "motivo_baja", length = 100)
    private String motivoBaja;

}
