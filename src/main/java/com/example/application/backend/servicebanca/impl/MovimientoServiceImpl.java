package com.example.application.backend.servicebanca.impl;


import com.example.application.backend.repositorybanca.MovimientoRepository;
import com.example.application.backend.servicebanca.MovimientoService;
import com.example.application.backend.daobanca.MovimientoDAO;
import com.example.application.backend.modelbanca.Cuenta;
import com.example.application.backend.modelbanca.Movimiento;
import com.example.application.backend.modelbanca.Usuario;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    //Inyección del repositorio
    private final MovimientoRepository movimientoRepository;
    private final MovimientoDAO movimientoDAO;

    public MovimientoServiceImpl(MovimientoRepository movimientoRepository, MovimientoDAO movimientoDAO) {
        this.movimientoRepository = movimientoRepository;
        this.movimientoDAO = movimientoDAO;
    }

    @Override
    public Movimiento creaMovimiento(Movimiento movimiento) {
        if(movimiento.getId() != null){
            return null;
        }else{
           return movimientoRepository.save(movimiento);
        }

    }

    /*
       Recupera Movimientos por IdUsuario y los filtra por un rango de Fecha y Categoria
        */
    @Override
    public List<Movimiento> recuperaMovimientosPorIdUsuarioFiltrados(Long id, Map<String, String> customQuery) {
        if (recuperaMovimientosPorIdUsuario(id) == null){
            return null;
        }
        return movimientoDAO.movimientosFiltrados(customQuery);
    }

    @Override
    public List<Movimiento> recuperaMovimientosPorIdUsuario(Long idUsuario) {

        // Todos los movimientos de la BBDD
        List<Movimiento> listMovimientos = movimientoRepository.findAll();

        // Aquí se guardarán los movimientos del usuario con id=idUsuario
        List<Movimiento> listResultado = new ArrayList<>();

        for (int i = 0; i < listMovimientos.size(); i++) {

            Cuenta cuenta = listMovimientos.get(i).getCuenta();

            List<Usuario> listadoUsuarios = cuenta.getUsers();

            for (int j = 0; j < listadoUsuarios.size(); j++) {
                if(listadoUsuarios.get(j).getId() == idUsuario)
                    listResultado.add(listMovimientos.get(i));
            }

        }
        return listResultado;
    }


    @Override
    public List<Movimiento> recuperaTodosMovimientos() {
        return movimientoRepository.findAll();
    }

    @Override
    public Optional<Movimiento> movimientoPorId(Long id) {

        Optional<Movimiento> movimiento = movimientoRepository.findById(id);

        return movimiento;
    }

    @Override
    public Double saldoTotalTarjeta(String numTarjeta) {
        List<Movimiento> listadoMovimientos = this.recuperaTodosMovimientos();
        Double saldoTotal = 0d;

        for (Movimiento movimiento:
                listadoMovimientos) {
            if(movimiento.getNumTarjeta().equals(numTarjeta)){
                saldoTotal += movimiento.getCantidad();
            }
        }

        return saldoTotal;
    }

    // Calcula el total por categorias en los últimos 'numMovimientos'
    @Override
    public Double totalPorCategoria(Long idUsuario, String categoria, Integer numMovimientos) {

        List<Movimiento> listadoMovimientos =  this.recuperaMovimientosPorIdUsuario(idUsuario);


        List<Movimiento> movimientoResultado = new ArrayList<>();
        int limiteMax = numMovimientos;
        for (int i = listadoMovimientos.size() - limiteMax; i < listadoMovimientos.size(); i++) {

            if(i < 0)  continue;

            movimientoResultado.add(listadoMovimientos.get(i));
        }

        Double total = 0d;

        for (Movimiento movimiento : movimientoResultado) {
            if(movimiento.getCategoria().getTipoCategoria().equals(categoria)){
                total += movimiento.getCantidad();
            }
        }

        return total;
    }


}
