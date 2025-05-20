package com.universidad.controller;

import com.universidad.model.Materia;
import com.universidad.service.IMateriaService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import com.universidad.dto.MateriaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Controlador REST para la gestión de materias.
 * Proporciona endpoints para operaciones CRUD, asignación de docentes y verificación de prerequisitos circulares.
 */
@RestController
@RequestMapping("/api/materias")
public class MateriaController {

    private final IMateriaService materiaService;
    private static final Logger logger = LoggerFactory.getLogger(MateriaController.class);

    /**
     * Constructor para la inyección de dependencias.
     * @param materiaService Servicio de materias.
     */
    @Autowired
    public MateriaController(IMateriaService materiaService) {
        this.materiaService = materiaService;
    }

    /**
     * Obtiene la lista de todas las materias.
     * @return Lista de MateriaDTO.
     */
    @GetMapping
    public ResponseEntity<List<MateriaDTO>> obtenerTodasLasMaterias() {
        long inicio = System.currentTimeMillis();
        logger.info("[MATERIA] Inicio obtenerTodasLasMaterias: {}", inicio);
        List<MateriaDTO> result = materiaService.obtenerTodasLasMaterias();
        long fin = System.currentTimeMillis();
        logger.info("[MATERIA] Fin obtenerTodasLasMaterias: {} (Duracion: {} ms)", fin, (fin - inicio));
        return ResponseEntity.ok(result);
    }

    /**
     * Obtiene una materia por su ID.
     * @param id ID de la materia.
     * @return MateriaDTO correspondiente o 404 si no existe.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MateriaDTO> obtenerMateriaPorId(@PathVariable Long id) {
        long inicio = System.currentTimeMillis();
        logger.info("[MATERIA] Inicio obtenerMateriaPorId: {}", inicio);
        MateriaDTO materia = materiaService.obtenerMateriaPorId(id);
        long fin = System.currentTimeMillis();
        logger.info("[MATERIA] Fin obtenerMateriaPorId: {} (Duracion: {} ms)", fin, (fin - inicio));
        if (materia == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(materia);
    }

    /**
     * Obtiene una materia por su código único.
     * @param codigoUnico Código único de la materia.
     * @return MateriaDTO correspondiente o 404 si no existe.
     */
    @GetMapping("/codigo/{codigoUnico}")
    public ResponseEntity<MateriaDTO> obtenerMateriaPorCodigoUnico(@PathVariable String codigoUnico) {
        MateriaDTO materia = materiaService.obtenerMateriaPorCodigoUnico(codigoUnico);
        if (materia == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(materia);
    }

    /**
     * Crea una nueva materia.
     * @param materia Datos de la materia a crear.
     * @return MateriaDTO creada.
     */
    @PostMapping
    public ResponseEntity<MateriaDTO> crearMateria(@Valid @RequestBody MateriaDTO materia) {
        MateriaDTO nueva = materiaService.crearMateria(materia);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    /**
     * Actualiza los datos de una materia existente.
     * @param id ID de la materia a actualizar.
     * @param materia Datos actualizados de la materia.
     * @return MateriaDTO actualizada.
     */
    @PutMapping("/{id}")
    public ResponseEntity<MateriaDTO> actualizarMateria(@PathVariable Long id, @RequestBody MateriaDTO materia) {
        MateriaDTO actualizadaDTO = materiaService.actualizarMateria(id, materia);
        return ResponseEntity.ok(actualizadaDTO);
    }

    /**
     * Asigna un docente a una materia.
     * @param id ID de la materia.
     * @param docenteId ID del docente a asignar.
     * @return MateriaDTO actualizada con el docente asignado.
     */
    @PutMapping("/{id}/asignar-docente/{docenteId}")
    public ResponseEntity<MateriaDTO> asignarDocente(
            @PathVariable Long id,
            @PathVariable Long docenteId) {
        MateriaDTO actualizada = materiaService.asignarDocente(id, docenteId);
        return ResponseEntity.ok(actualizada);
    }

    /**
     * Elimina una materia por su ID.
     * @param id ID de la materia a eliminar.
     * @return Respuesta sin contenido si la eliminación fue exitosa.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMateria(@PathVariable Long id) {
        materiaService.eliminarMateria(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Verifica si agregar un prerequisito a una materia formaría un círculo (ciclo) en la relación de prerequisitos.
     * @param materiaId ID de la materia principal.
     * @param prerequisitoId ID del posible prerequisito.
     * @return true si formaría un círculo, false en caso contrario. Retorna 400 si hay ciclo, 404 si la materia no existe.
     */
    @GetMapping("/formaria-circulo/{materiaId}/{prerequisitoId}")
    @Transactional
    public ResponseEntity<Boolean> formariaCirculo(@PathVariable Long materiaId, @PathVariable Long prerequisitoId) {
        MateriaDTO materiaDTO = materiaService.obtenerMateriaPorId(materiaId);
        if (materiaDTO == null) {
            return ResponseEntity.notFound().build();
        }
        Materia materia = new Materia(materiaDTO.getId(), materiaDTO.getNombreMateria(), materiaDTO.getCodigoUnico());
        boolean circulo = materia.formariaCirculo(prerequisitoId);
        if (circulo) {
            return ResponseEntity.badRequest().body(circulo);
        }
        return ResponseEntity.ok(circulo);
    }
}