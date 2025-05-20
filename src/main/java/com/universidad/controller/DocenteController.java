package com.universidad.controller;

import com.universidad.dto.DocenteDTO;
import com.universidad.model.Docente;
import com.universidad.service.IDocenteService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Controlador REST para la gestión de docentes.
 * Proporciona endpoints para operaciones CRUD y consultas específicas sobre docentes.
 */
@RestController
@RequestMapping("/api/docentes")
@Validated
public class DocenteController {

    private final IDocenteService docenteService;
    private static final Logger logger = LoggerFactory.getLogger(DocenteController.class);

    /**
     * Constructor para inyección de dependencias.
     * @param docenteService Servicio de docentes.
     */
    @Autowired
    public DocenteController(IDocenteService docenteService) {
        this.docenteService = docenteService;
    }

    /**
     * Obtiene la lista de todos los docentes.
     * @return Lista de DocenteDTO.
     */
    @GetMapping
    public ResponseEntity<List<DocenteDTO>> obtenerTodosLosDocentes() {
        long inicio = System.currentTimeMillis();
        logger.info("[DOCENTE] Inicio obtenerTodosLosDocentes: {}", inicio);
        List<DocenteDTO> docentes = docenteService.obtenerTodosLosDocentes();
        long fin = System.currentTimeMillis();
        logger.info("[DOCENTE] Fin obtenerTodosLosDocentes: {} (Duracion: {} ms)", fin, (fin-inicio));
        return ResponseEntity.ok(docentes);
    }

    /**
     * Obtiene un docente por su número de empleado.
     * @param nroEmpleado Número de empleado del docente.
     * @return DocenteDTO correspondiente.
     */
    @GetMapping("/empleado/{nroEmpleado}")
    public ResponseEntity<DocenteDTO> obtenerDocentePorNroEmpleado(
        @PathVariable String nroEmpleado) {
        long inicio = System.currentTimeMillis();
        logger.info("[DOCENTE] Inicio obtenerDocentePorNroEmpleado: {}", inicio);
        DocenteDTO docente = docenteService.obtenerDocentePorNroEmpleado(nroEmpleado);
        long fin = System.currentTimeMillis();
        logger.info("[DOCENTE] Fin obtenerDocentePorNroEmpleado: {} (Duracion: {} ms)", fin, (fin-inicio));
        return ResponseEntity.ok(docente);
    }

    /**
     * Obtiene un docente por su ID aplicando un bloqueo de registro.
     * @param id ID del docente.
     * @return Docente con bloqueo.
     */
    @GetMapping("/{id}/lock")
    public ResponseEntity<Docente> getDocenteConBloqueo(
        @PathVariable Long id) {
        Docente docente = docenteService.obtenerDocenteConBloqueo(id);
        return ResponseEntity.ok(docente);
    }

    /**
     * Crea un nuevo docente.
     * @param docenteDTO Datos del docente a crear.
     * @return DocenteDTO creado.
     */
    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<DocenteDTO> crearDocente(@Valid @RequestBody DocenteDTO docenteDTO) {
        DocenteDTO nuevoDocente = docenteService.crearDocente(docenteDTO);
        return ResponseEntity.status(201).body(nuevoDocente);
    }

    /**
     * Actualiza los datos de un docente existente.
     * @param id ID del docente a actualizar.
     * @param docenteDTO Datos actualizados.
     * @return DocenteDTO actualizado.
     */
    @PutMapping("/{id}")
    @Transactional
    @ResponseStatus(HttpStatus.OK)    
    public ResponseEntity<DocenteDTO> actualizarDocente(
        @PathVariable Long id,
        @RequestBody DocenteDTO docenteDTO) {
        DocenteDTO docenteActualizado = docenteService.actualizarDocente(id, docenteDTO);
        return ResponseEntity.ok(docenteActualizado);
    }

    /**
     * Da de baja (elimina lógicamente) a un docente.
     * @param id ID del docente a dar de baja.
     * @param docenteDTO Datos del docente.
     * @return DocenteDTO dado de baja.
     */
    @PutMapping("/{id}/baja")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<DocenteDTO> eliminarDocente(
        @PathVariable Long id,
        @RequestBody DocenteDTO docenteDTO) {
        DocenteDTO docenteEliminado = docenteService.eliminarDocente(id, docenteDTO);
        return ResponseEntity.ok(docenteEliminado);
    }

    /**
     * Obtiene la lista de docentes activos.
     * @return Lista de DocenteDTO activos.
     */
    @GetMapping("/activos")
    public ResponseEntity<List<DocenteDTO>> obtenerDocenteActivo() {
        List<DocenteDTO> docentesActivos = docenteService.obtenerDocentesActivos();
        return ResponseEntity.ok(docentesActivos);
    }
}