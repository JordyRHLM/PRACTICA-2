package com.universidad.service;

import com.universidad.dto.DocenteDTO;
import com.universidad.model.Docente;

import java.util.List;

public interface IDocenteService {
    List<DocenteDTO> obtenerTodosLosDocentes();
    List<DocenteDTO> obtenerDocentesActivos();
    DocenteDTO obtenerDocentePorNroEmpleado(String nroEmpleado);
    DocenteDTO crearDocente(DocenteDTO docenteDTO);
    DocenteDTO actualizarDocente(Long id, DocenteDTO docenteDTO);
    DocenteDTO eliminarDocente(Long id, DocenteDTO docenteDTO);
    Docente obtenerDocenteConBloqueo(Long id);
}