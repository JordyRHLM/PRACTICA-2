package com.universidad.service.impl;

import com.universidad.dto.InscripcionDTO;
import com.universidad.dto.EstudianteDTO;
import com.universidad.dto.MateriaDTO;
import com.universidad.model.Estudiante;
import com.universidad.model.Inscripcion;
import com.universidad.model.Materia;
import com.universidad.repository.EstudianteRepository;
import com.universidad.repository.InscripcionRepository;
import com.universidad.repository.MateriaRepository;
import com.universidad.service.IInscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InscripcionServiceImpl implements IInscripcionService {

    @Autowired
    private InscripcionRepository inscripcionRepository;
    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private MateriaRepository materiaRepository;

    private InscripcionDTO mapToDTO(Inscripcion inscripcion) {
        InscripcionDTO dto = new InscripcionDTO();
        dto.setId(inscripcion.getId());
        dto.setEstudianteId(inscripcion.getEstudiante().getId());
        dto.setMateriaId(inscripcion.getMateria().getId());
        dto.setFechaInscripcion(inscripcion.getFechaInscripcion());

        Estudiante estudiante = inscripcion.getEstudiante();
        Materia materia = inscripcion.getMateria();
        EstudianteDTO estudianteDTO = new EstudianteDTO();
        estudianteDTO.setId(estudiante.getId());
        estudianteDTO.setNombre(estudiante.getNombre());
        estudianteDTO.setApellido(estudiante.getApellido());
        estudianteDTO.setEmail(estudiante.getEmail());

        MateriaDTO materiaDTO = new MateriaDTO();
        materiaDTO.setId(materia.getId());
        materiaDTO.setNombreMateria(materia.getNombreMateria());
        materiaDTO.setCodigoUnico(materia.getCodigoUnico());

        //dto.setEstudiante(estudianteDTO);
        //dto.setMateria(materiaDTO);

        return dto;
    }

    @Override
    public InscripcionDTO crearInscripcion(InscripcionDTO dto) {
        Estudiante estudiante = estudianteRepository.findById(dto.getEstudianteId())
            .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        Materia materia = materiaRepository.findById(dto.getMateriaId())
            .orElseThrow(() -> new IllegalArgumentException("Materia no encontrada"));
        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEstudiante(estudiante);
        inscripcion.setMateria(materia);
        inscripcion.setFechaInscripcion(dto.getFechaInscripcion());
        return mapToDTO(inscripcionRepository.save(inscripcion));
    }

    @Override
    public InscripcionDTO obtenerInscripcion(Long id) {
        return inscripcionRepository.findById(id).map(this::mapToDTO).orElse(null);
    }

    @Override
    public List<InscripcionDTO> obtenerTodasLasInscripciones() {
        return inscripcionRepository.findAll()
            .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public List<InscripcionDTO> obtenerInscripcionesPorEstudiante(Long estudianteId) {
        return inscripcionRepository.findByEstudianteId(estudianteId)
            .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    @Override
    public InscripcionDTO actualizarInscripcion(Long id, InscripcionDTO dto) {
        Inscripcion inscripcion = inscripcionRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Inscripción no encontrada"));
        Estudiante estudiante = estudianteRepository.findById(dto.getEstudianteId())
            .orElseThrow(() -> new IllegalArgumentException("Estudiante no encontrado"));
        Materia materia = materiaRepository.findById(dto.getMateriaId())
            .orElseThrow(() -> new IllegalArgumentException("Materia no encontrada"));
        inscripcion.setEstudiante(estudiante);
        inscripcion.setMateria(materia);
        inscripcion.setFechaInscripcion(dto.getFechaInscripcion());
        return mapToDTO(inscripcionRepository.save(inscripcion));
    }

    @Override
    public void eliminarInscripcion(Long id) {
        inscripcionRepository.deleteById(id);
    }
}