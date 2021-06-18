package com.example.application.backend.servicebanca;

import com.example.application.backend.modelbanca.Prestamo;

import java.util.List;
import java.util.Optional;

public interface PrestamoService {

    Optional<Prestamo> findById(Long id);

    List<Prestamo> findAll();

    void createPrestamo(Prestamo prestamo);

    void updatePrestamo(Prestamo prestamo);

    void deletePrestamo(Long id);
}
