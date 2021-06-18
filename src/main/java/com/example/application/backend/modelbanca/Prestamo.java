package com.example.application.backend.modelbanca;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="prestamo")
public class Prestamo {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Cantidad es necesaria")
    @Column (name="cantidad")
    private Double cantidad;

    @NotNull(message = "Duración en meses es necesaria")
    @Column (name="duracion")
    private Double duracion;

    @NotNull(message = "Interés es necesario")
    @Column(name="tipo_interes")
    private Double tipoInteres;

    @JsonIgnore
    @NotNull(message ="Cuenta de Ingreso es necesaria")
    @ManyToOne
    @JoinColumn(name="id_cuenta_ingreso")
    private Cuenta cuentaIngreso;

    @JsonIgnore
    @NotNull(message ="Cuenta de Cobro es necesaria")
    @ManyToOne
    @JoinColumn(name="id_cuenta_cobro")
    private Cuenta cuentaCobro;

    public Prestamo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getDuracion() {
        return duracion;
    }

    public void setDuracion(Double duracion) {
        this.duracion = duracion;
    }

    public Cuenta getCuentaIngreso() {
        return cuentaIngreso;
    }

    public void setCuentaIngreso(Cuenta cuentaIngreso) {
        this.cuentaIngreso = cuentaIngreso;
    }

    public Cuenta getCuentaCobro() {
        return cuentaCobro;
    }

    public void setCuentaCobro(Cuenta cuentaCobro) {
        this.cuentaCobro = cuentaCobro;
    }

    public Double getTipoInteres() {
        return tipoInteres;
    }

    public void setTipoInteres(Double tipoInteres) {
        this.tipoInteres = tipoInteres;
    }
}
