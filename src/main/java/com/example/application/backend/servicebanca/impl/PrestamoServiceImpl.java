package com.example.application.backend.servicebanca.impl;

import com.example.application.backend.modelbanca.Prestamo;
import com.example.application.backend.repositorybanca.PrestamoRepository;
import com.example.application.backend.servicebanca.PrestamoService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;

    public PrestamoServiceImpl(PrestamoRepository prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    @Override
    public Optional<Prestamo> findById(Long id) {
        if(id == null){
            return Optional.empty();
        }
        return prestamoRepository.findById(id);
    }

    @Override
    public List<Prestamo> findAll() {
        return prestamoRepository.findAll();
    }

    @Override
    public void createPrestamo(Prestamo prestamo) {
        if(ObjectUtils.isEmpty(prestamo)){
            return;
        }
        prestamoRepository.save(prestamo);
    }

    @Override
    public void updatePrestamo(Prestamo prestamo) {
        if(ObjectUtils.isEmpty(prestamo)){
            return;
        }
        prestamoRepository.save(prestamo);
    }

    @Override
    public void deletePrestamo(Long id) {
        if (id != null && prestamoRepository.existsById(id))
            prestamoRepository.deleteById(id);
    }
}
