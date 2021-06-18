package com.example.application.backend.daobanca;

import com.example.application.backend.modelbanca.Movimiento;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface MovimientoDAO {

    List<Movimiento> movimientosFiltrados(Map<String, String> customQuery);

}
