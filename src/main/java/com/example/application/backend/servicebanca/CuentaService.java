package com.example.application.backend.servicebanca;



import com.example.application.backend.modelbanca.Cuenta;

import java.util.List;
import java.util.Optional;

public interface CuentaService {

    // Recupera las cuentas asociadas(listado String) a un usuario por su id
    List<String> cuentasDeUsuarioPorId(Long idUsuario);

    // Recupera el saldo de una cuenta por su numero de cuenta (String)
    Double saldoDeCuenta(String numCuenta);

    // Recupera Cuenta por Id
    Optional <Cuenta> recuperarCuentaPorId(Long id);

    // Recupera todas las cuentas
    List<Cuenta> listadoCompletoCuentas ();

    // Crea una cuenta
    Cuenta crearCuenta(Cuenta cuenta);

    List<Cuenta> findAll();

    // Recupera listado de Cuentas de un usuario por su Id
    List<Cuenta> encuentraCuentasDeUsuario(Long idUsuario);

    // Actualiza una Cuenta
    Cuenta actualizaCuenta(Cuenta cuenta);
}
