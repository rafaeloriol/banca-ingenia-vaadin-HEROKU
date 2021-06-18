package com.example.application.backend.repositorybanca;

import com.example.application.backend.modelbanca.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrestamoRepository extends JpaRepository <Prestamo, Long> {



}
