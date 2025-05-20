package com.universidad.service;

import com.universidad.dto.InscripcionDTO;
import java.util.List;

/**
 * Interfaz para el servicio de gestión de inscripciones.
 * Define las operaciones CRUD y de consulta relacionadas con la entidad Inscripcion.
 */
public interface IInscripcionService {

    /**
     * Crea una nueva inscripción.
     * @param inscripcion Datos de la inscripción a crear.
     * @return InscripcionDTO creada.
     */
    InscripcionDTO crearInscripcion(InscripcionDTO inscripcion);

    /**
     * Obtiene una inscripción por su ID.
     * @param id ID de la inscripción.
     * @return InscripcionDTO correspondiente o null si no existe.
     */
    InscripcionDTO obtenerInscripcion(Long id);

    /**
     * Obtiene la lista de todas las inscripciones.
     * @return Lista de InscripcionDTO.
     */
    List<InscripcionDTO> obtenerTodasLasInscripciones();

    /**
     * Obtiene todas las inscripciones realizadas por un estudiante específico.
     * @param estudianteId ID del estudiante.
     * @return Lista de InscripcionDTO asociadas al estudiante.
     */
    List<InscripcionDTO> obtenerInscripcionesPorEstudiante(Long estudianteId);

    /**
     * Actualiza los datos de una inscripción existente.
     * @param id ID de la inscripción a actualizar.
     * @param inscripcion Datos actualizados de la inscripción.
     * @return InscripcionDTO actualizada.
     */
    InscripcionDTO actualizarInscripcion(Long id, InscripcionDTO inscripcion);

    /**
     * Elimina una inscripción por su ID.
     * @param id ID de la inscripción a eliminar.
     */
    void eliminarInscripcion(Long id);
}
