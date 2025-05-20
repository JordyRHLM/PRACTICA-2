package com.universidad.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.List;

/**
 * Entidad que representa una materia en el sistema de gestión universitaria.
 * Incluye información sobre nombre, código, créditos, docente asignado y relaciones de prerequisitos.
 */
@Getter // Genera un getter para todos los campos de la clase
@Setter // Genera un setter para todos los campos de la clase
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "materia") // Nombre de la tabla en la base de datos
public class Materia implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor parcial para inicializar una materia con ID, nombre y código único.
     * @param id Identificador único de la materia.
     * @param nombreMateria Nombre de la materia.
     * @param codigoUnico Código único de la materia.
     */
    public Materia(Long id, String nombreMateria, String codigoUnico) {
        this.id = id;
        this.nombreMateria = nombreMateria;
        this.codigoUnico = codigoUnico;
    }

    /**
     * Identificador único de la materia. Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id_materia")
    private Long id;

    /**
     * Nombre de la materia. No puede ser nulo y tiene un máximo de 100 caracteres.
     */
    @Column(name = "nombre_materia", nullable = false, length = 100)
    private String nombreMateria;

    /**
     * Código único de la materia. No puede ser nulo y debe ser único en la base de datos.
     */
    @Column(name = "codigo_unico", nullable = false, unique = true)
    private String codigoUnico;

    /**
     * Número de créditos de la materia. No puede ser nulo.
     */
    @Column(name = "creditos", nullable = false)
    private Integer creditos;

    /*
     * Campo para manejo de versiones de la entidad (control de concurrencia optimista).
     * Descomentarlo si se requiere control de versiones.
     *
     * @Version
     * private Long version;
     */

    /**
     * Docente asignado a la materia.
     */
    @ManyToOne
    @JoinColumn(name = "docente_id")
    private Docente docente;

    /**
     * Lista de materias que son prerequisitos para esta materia.
     */
    @ManyToMany
    @JoinTable(
        name = "materia_prerequisito",
        joinColumns = @JoinColumn(name = "id_materia"),
        inverseJoinColumns = @JoinColumn(name = "id_prerequisito")
    )
    private List<Materia> prerequisitos;

    /**
     * Lista de materias para las que esta materia es prerequisito.
     */
    @ManyToMany(mappedBy = "prerequisitos")
    private List<Materia> esPrerequisitoDe;

    /**
     * Verifica si agregar la materia con el ID dado como prerequisito formaría un ciclo.
     * @param prerequisitoId ID de la materia candidata a prerequisito.
     * @return true si se formaría un ciclo, false en caso contrario.
     */
    public boolean formariaCirculo(Long prerequisitoId) {
        return formariaCirculoRecursivo(this.getId(), prerequisitoId, new java.util.HashSet<>());
    }

    /**
     * Método auxiliar recursivo para detectar ciclos en la relación de prerequisitos.
     * @param objetivoId ID de la materia original.
     * @param actualId ID de la materia actual en la recursión.
     * @param visitados Conjunto de IDs ya visitados para evitar bucles infinitos.
     * @return true si se detecta un ciclo, false en caso contrario.
     */
    private boolean formariaCirculoRecursivo(Long objetivoId, Long actualId, java.util.Set<Long> visitados) {
        if (objetivoId == null || actualId == null)
            return false;
        if (objetivoId.equals(actualId))
            return true;
        if (!visitados.add(actualId))
            return false;
        if (this.getPrerequisitos() == null)
            return false;
        for (Materia prereq : this.getPrerequisitos()) {
            if (prereq != null && prereq.getId() != null && prereq.getId().equals(actualId)) {
                if (prereq.getPrerequisitos() != null) {
                    for (Materia subPrereq : prereq.getPrerequisitos()) {
                        if (formariaCirculoRecursivo(objetivoId, subPrereq.getId(), visitados)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

}