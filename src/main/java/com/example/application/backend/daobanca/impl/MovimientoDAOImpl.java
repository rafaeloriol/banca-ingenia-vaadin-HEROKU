package com.example.application.backend.daobanca.impl;

import com.example.application.backend.daobanca.MovimientoDAO;
import com.example.application.backend.modelbanca.Categoria;
import com.example.application.backend.modelbanca.Movimiento;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class MovimientoDAOImpl implements MovimientoDAO {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Movimiento> movimientosFiltrados(Map<String, String> customQuery) {
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Movimiento> cq =cb.createQuery(Movimiento.class);
        Root<Movimiento> movimientoRoot = cq.from(Movimiento.class);
        List predicados = new ArrayList();
        if (customQuery.containsKey("fechaOperacion")) {
            Predicate predicadoFechaOperacion = cb.like(movimientoRoot.get("fechaOperacion"), customQuery.get("fechaOperacion"));
            predicados.add(predicadoFechaOperacion);
        }
        else if (customQuery.containsKey("fechaInicio") && customQuery.containsKey("fechaFin")) {
            LocalDate fechaInicio = LocalDate.parse(customQuery.get("fechaInicio"));
            LocalDate fechaFin = LocalDate.parse(customQuery.get("fechaFin"));
            Predicate predicadoRangoFechas = cb.between(movimientoRoot.get("fechaOperacion"), fechaInicio, fechaFin);
            predicados.add(predicadoRangoFechas);
        }
        if (customQuery.containsKey("categoria")) {
            Join<Movimiento, Categoria> categoria = movimientoRoot.join("categoria");
            Expression<String> expression = categoria.get("tipoCategoria");
            Predicate predicadoCategoria = cb.like(expression, customQuery.get("categoria"));
            predicados.add(predicadoCategoria);
        }
            //Convierto la lista de predicados a un array
            Predicate[] arrayPredicados = (Predicate[]) predicados.toArray(new Predicate[0]);
            cq.where(arrayPredicados);
            return manager.createQuery(cq).getResultList();
    }
}
