package com.universidad.service;

import com.universidad.dto.MateriaDTO;
import com.universidad.model.Materia;
import java.util.List;

/**
 * Interfaz para el servicio de gestión de materias.
 * Define las operaciones CRUD y de negocio relacionadas con la entidad Materia.
 */
public interface IMateriaService {

    /**
     * Obtiene la lista de todas las materias.
     * @return Lista de MateriaDTO.
     */
    List<MateriaDTO> obtenerTodasLasMaterias();

    /**
     * Obtiene una materia por su ID.
     * @param id ID de la materia.
     * @return MateriaDTO correspondiente o null si no existe.
     */
    MateriaDTO obtenerMateriaPorId(Long id);

    /**
     * Obtiene una materia por su código único.
     * @param codigoUnico Código único de la materia.
     * @return MateriaDTO correspondiente o null si no existe.
     */
    MateriaDTO obtenerMateriaPorCodigoUnico(String codigoUnico);

    /**
     * Crea una nueva materia.
     * @param materia Datos de la materia a crear.
     * @return MateriaDTO creada.
     */
    MateriaDTO crearMateria(MateriaDTO materia);

    /**
     * Actualiza los datos de una materia existente.
     * @param id ID de la materia a actualizar.
     * @param materia Datos actualizados de la materia.
     * @return MateriaDTO actualizada.
     */
    MateriaDTO actualizarMateria(Long id, MateriaDTO materia);

    /**
     * Elimina una materia por su ID.
     * @param id ID de la materia a eliminar.
     */
    void eliminarMateria(Long id);

    /**
     * Asigna un docente a una materia.
     * @param id ID de la materia.
     * @param docenteId ID del docente a asignar.
     * @return MateriaDTO actualizada con el docente asignado.
     */
    MateriaDTO asignarDocente(Long id, Long docenteId);
}
