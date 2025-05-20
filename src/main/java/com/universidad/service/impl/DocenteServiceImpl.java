package com.universidad.service.impl;

import com.universidad.dto.DocenteDTO;
import com.universidad.model.Docente;
import com.universidad.repository.DocenteRepository;
import com.universidad.service.IDocenteService;
import com.universidad.validation.DocenteValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DocenteServiceImpl implements IDocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private DocenteValidator docenteValidator;

    @Override
    @Cacheable(value = "docentes")
    public List<DocenteDTO> obtenerTodosLosDocentes() {
        return docenteRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "docentesActivos")
    public List<DocenteDTO> obtenerDocentesActivos() {
        return docenteRepository.findAll().stream()
                .filter(docente -> "activo".equalsIgnoreCase(docente.getEstado()))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "docente", key = "#nroEmpleado")
    public DocenteDTO obtenerDocentePorNroEmpleado(String nroEmpleado) {
        Docente docente = docenteRepository.findByNroEmpleado(nroEmpleado);
        return convertToDTO(docente);
    }

    @Override
    @CachePut(value = "docente", key = "#result.nroEmpleado")
    @CacheEvict(value = {"docentes", "docentesActivos"}, allEntries = true)
    public DocenteDTO crearDocente(DocenteDTO docenteDTO) {
        docenteValidator.validarDocente(docenteDTO);
        
        Docente docente = convertToEntity(docenteDTO);
        Docente docenteGuardado = docenteRepository.save(docente);
        return convertToDTO(docenteGuardado);
    }

    @Override
    @CachePut(value = "docente", key = "#id")
    @CacheEvict(value = {"docentes", "docentesActivos"}, allEntries = true)
    public DocenteDTO actualizarDocente(Long id, DocenteDTO docenteDTO) {
        Docente docenteExistente = docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
        
        docenteExistente.setNombre(docenteDTO.getNombre());
        docenteExistente.setApellido(docenteDTO.getApellido());
        docenteExistente.setEmail(docenteDTO.getEmail());
        docenteExistente.setFechaNacimiento(docenteDTO.getFechaNacimiento());
        docenteExistente.setNroEmpleado(docenteDTO.getNroEmpleado());
        docenteExistente.setDepartamento(docenteDTO.getDepartamento());
        docenteExistente.setUsuarioModificacion("admin");
        docenteExistente.setFechaModificacion(LocalDate.now());

        Docente docenteActualizado = docenteRepository.save(docenteExistente);
        return convertToDTO(docenteActualizado);
    }

    @Override
    @CacheEvict(value = {"docente", "docentes", "docentesActivos"}, allEntries = true)
    public DocenteDTO eliminarDocente(Long id, DocenteDTO docenteDTO) {
        Docente docenteExistente = docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
        
        docenteExistente.setEstado("inactivo");
        docenteExistente.setUsuarioBaja("admin");
        docenteExistente.setFechaBaja(LocalDate.now());
        docenteExistente.setMotivoBaja(docenteDTO.getMotivoBaja());

        Docente docenteInactivo = docenteRepository.save(docenteExistente);
        return convertToDTO(docenteInactivo);
    }

    @Transactional
    public Docente obtenerDocenteConBloqueo(Long id) {
        Docente docente = docenteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
        try { Thread.sleep(15000); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        return docente;
    }

    private DocenteDTO convertToDTO(Docente docente) {
        return DocenteDTO.builder()
                .id(docente.getId())
                .nombre(docente.getNombre())
                .apellido(docente.getApellido())
                .email(docente.getEmail())
                .fechaNacimiento(docente.getFechaNacimiento())
                .nroEmpleado(docente.getNroEmpleado())
                .departamento(docente.getDepartamento())
                .estado(docente.getEstado())
                .usuarioAlta(docente.getUsuarioAlta())
                .fechaAlta(docente.getFechaAlta())
                .usuarioModificacion(docente.getUsuarioModificacion())
                .fechaModificacion(docente.getFechaModificacion())
                .usuarioBaja(docente.getUsuarioBaja())
                .fechaBaja(docente.getFechaBaja())
                .motivoBaja(docente.getMotivoBaja())
                .build();
    }
    
    private Docente convertToEntity(DocenteDTO docenteDTO) {
        return Docente.builder()
                .id(docenteDTO.getId())
                .nombre(docenteDTO.getNombre())
                .apellido(docenteDTO.getApellido())
                .email(docenteDTO.getEmail())
                .fechaNacimiento(docenteDTO.getFechaNacimiento())
                .nroEmpleado(docenteDTO.getNroEmpleado())
                .departamento(docenteDTO.getDepartamento())
                .usuarioAlta(docenteDTO.getUsuarioAlta())
                .fechaAlta(docenteDTO.getFechaAlta())
                .usuarioModificacion(docenteDTO.getUsuarioModificacion())
                .fechaModificacion(docenteDTO.getFechaModificacion())
                .estado(docenteDTO.getEstado())
                .usuarioBaja(docenteDTO.getUsuarioBaja())
                .fechaBaja(docenteDTO.getFechaBaja())
                .motivoBaja(docenteDTO.getMotivoBaja())
                .build();
    }
}