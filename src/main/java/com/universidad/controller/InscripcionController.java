package com.universidad.controller;

import com.universidad.dto.InscripcionDTO;
import com.universidad.service.IInscripcionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de inscripciones.
 * Proporciona endpoints para operaciones CRUD y consultas relacionadas con inscripciones de estudiantes a materias.
 */
@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    @Autowired
    private IInscripcionService inscripcionService;

    /**
     * Crea una nueva inscripción.
     * @param inscripcion Datos de la inscripción a crear.
     * @return InscripcionDTO creada.
     */
    @PostMapping
    public ResponseEntity<InscripcionDTO> crear(@Valid @RequestBody InscripcionDTO inscripcion) {
        return ResponseEntity.ok(inscripcionService.crearInscripcion(inscripcion));
    }

    /**
     * Obtiene una inscripción por su ID.
     * @param id ID de la inscripción.
     * @return InscripcionDTO correspondiente o 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<InscripcionDTO> obtener(@PathVariable Long id) {
        InscripcionDTO inscripcion = inscripcionService.obtenerInscripcion(id);
        if (inscripcion == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(inscripcion);
    }

    /**
     * Obtiene la lista de todas las inscripciones.
     * @return Lista de InscripcionDTO.
     */
    @GetMapping
    public ResponseEntity<List<InscripcionDTO>> listarTodas() {
        return ResponseEntity.ok(inscripcionService.obtenerTodasLasInscripciones());
    }

    /**
     * Obtiene todas las inscripciones realizadas por un estudiante específico.
     * @param estudianteId ID del estudiante.
     * @return Lista de InscripcionDTO asociadas al estudiante.
     */
    @GetMapping("/estudiante/{estudianteId}")
    public ResponseEntity<List<InscripcionDTO>> listarPorEstudiante(@PathVariable Long estudianteId) {
        return ResponseEntity.ok(inscripcionService.obtenerInscripcionesPorEstudiante(estudianteId));
    }

    /**
     * Actualiza los datos de una inscripción existente.
     * @param id ID de la inscripción a actualizar.
     * @param inscripcion Datos actualizados de la inscripción.
     * @return InscripcionDTO actualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<InscripcionDTO> actualizar(@PathVariable Long id, @Valid @RequestBody InscripcionDTO inscripcion) {
        return ResponseEntity.ok(inscripcionService.actualizarInscripcion(id, inscripcion));
    }

    /**
     * Elimina una inscripción por su ID.
     * @param id ID de la inscripción a eliminar.
     * @return Respuesta sin contenido si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inscripcionService.eliminarInscripcion(id);
        return ResponseEntity.noContent().build();
    }
}