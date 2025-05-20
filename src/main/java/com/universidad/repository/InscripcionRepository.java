package com.universidad.repository;

import com.universidad.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositorio para la entidad Inscripcion.
 * Proporciona métodos para acceder y manipular las inscripciones en la base de datos.
 */
public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {

    /**
     * Busca todas las inscripciones realizadas por un estudiante específico.
     * @param estudianteId ID del estudiante.
     * @return Lista de inscripciones asociadas al estudiante.
     */
    List<Inscripcion> findByEstudianteId(Long estudianteId);

    /**
     * Verifica si existe una inscripción de un estudiante a una materia específica.
     * @param estudianteId ID del estudiante.
     * @param materiaId ID de la materia.
     * @return true si existe la inscripción, false en caso contrario.
     */
    boolean existsByEstudianteIdAndMateriaId(Long estudianteId, Long materiaId);

    /**
     * Verifica si existe una inscripción de un estudiante a una materia específica, excluyendo una inscripción por ID.
     * @param estudianteId ID del estudiante.
     * @param materiaId ID de la materia.
     * @param id ID de la inscripción a excluir.
     * @return true si existe la inscripción, false en caso contrario.
     */
    boolean existsByEstudianteIdAndMateriaIdAndIdNot(Long estudianteId, Long materiaId, Long id);

    /**
     * Valida que no exista una inscripción duplicada para un estudiante en una materia específica.
     * @param estudianteId ID del estudiante.
     * @param materiaId ID de la materia.
     * @param inscripcionId ID de la inscripción a excluir de la validación.
     * @throws IllegalArgumentException si ya existe una inscripción duplicada.
     */
    default void validaNoInscripcionDuplicada(Long estudianteId, Long materiaId, Long inscripcionId) {
        if (existsByEstudianteIdAndMateriaIdAndIdNot(estudianteId, materiaId, inscripcionId)) {
            throw new IllegalArgumentException("El estudiante ya está inscrito en esta materia.");
        }
    }
}