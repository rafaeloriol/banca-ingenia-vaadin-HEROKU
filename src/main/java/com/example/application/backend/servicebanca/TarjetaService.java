package com.example.application.backend.servicebanca;


import com.example.application.backend.modelbanca.Tarjeta;

import java.util.List;

public interface TarjetaService {

    List<String> tarjetasDeCuentasPorId (Long idCuenta);

    List<String> tarjetasPorIdUsuario (Long idUsuario);

    // Crea una tarjeta
    Tarjeta crearTarjeta(Tarjeta tarjeta);

    // Encuentra todas las tarjetas
    List<Tarjeta> encuentraTarjetas();

    // Borrar una tarjeta
    void borrarTarjeta(Tarjeta tarjeta);

    // Recuperar las Tarjetas de un Usuario por su Id
    List<Tarjeta> tarjetasUsuarioPorId(Long id);

}
