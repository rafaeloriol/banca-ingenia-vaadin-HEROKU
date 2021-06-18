package com.example.application.views.inicio;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class CardTarjeta extends VerticalLayout {

    private String nombreEntidad;
    private Double saldo;
    private String numeroTarjeta;
    private String tipoTarjeta;
    // todo -- campo para el icono

    public CardTarjeta(String nombreEntidad, Double saldoCuenta, String numTarjeta, String tipoDeTarjeta) {
        this.nombreEntidad = nombreEntidad;
        this.saldo = saldoCuenta;
        this.numeroTarjeta = numTarjeta;
        this.tipoTarjeta = tipoDeTarjeta;

        // Estilos de la cardTarjeta
        this.setWidthFull();
        this.setMinWidth("200px");
        this.setMaxWidth("200px");

        // Sombra de la card
        this.getStyle().set("box-shadow", "5px 10px 8px #888888");

        // color de fondo
        this.getStyle().set("background-color","#E8DAEF");

        // Borde
        this.getStyle().set("border", "1px solid #800080");
        this.getStyle().set("border-radius",  "20px");


        H3 hEntidad =new H3(nombreEntidad);
        hEntidad.setWidthFull();
        hEntidad.getStyle().set("fontWeight", "bold");



        H3 hSaldo = new H3(saldo.toString() + " €");
        hSaldo.getStyle().set("color", "#800080");
        hSaldo.getStyle().set("margin","11px 0px 11px 0px");



        HorizontalLayout layoutTarjeta = new HorizontalLayout();

        H3 hNumeroTarjeta = new H3(numeroTarjeta);
        hNumeroTarjeta.getStyle().set("margin", "0px 0px 0px 27px");
//        H3 hTipoTarjeta = new H3(tipoTarjeta);

        Image imagenIcono = new Image();
        switch(tipoTarjeta){
            case "Débito":
                imagenIcono = new Image("images/icons8-visa-40.png", "Visa");
                imagenIcono.setWidth("30px");
                imagenIcono.setHeight("30px");
                break;
            case "Crédito":
                imagenIcono = new Image("images/icons8-mastercard-40.png", "MasterCard");
                imagenIcono.setWidth("30px");
                imagenIcono.setHeight("30px");
                break;
            default:
                break;
        }



        layoutTarjeta.add(imagenIcono, hNumeroTarjeta);

        add(hEntidad , hSaldo, layoutTarjeta);
    }
}
