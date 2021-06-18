package com.example.application.views.tarjeta;

import com.example.application.backend.modelbanca.Cuenta;
import com.example.application.backend.modelbanca.Tarjeta;
import com.example.application.backend.servicebanca.CuentaService;
import com.example.application.backend.servicebanca.TarjetaService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.*;
import com.vaadin.flow.data.validator.DateRangeValidator;
import com.vaadin.flow.data.validator.DoubleRangeValidator;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;

public class TarjetaForm extends Dialog {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // Inyección de Servicio
    private final TarjetaService tarjetaService;
    private final CuentaService cuentaService;

    // Constructor
    public TarjetaForm(Tarjeta tarjeta, TarjetaService tarjetaService, CuentaService cuentaService) {
        this.tarjetaService = tarjetaService;
        this.cuentaService = cuentaService;


        FormLayout layoutWithBinder = new FormLayout();
        Binder<Tarjeta> binder = new Binder<>();

        // Entidad Tarjeta que se va a bindear
        Tarjeta tarjetaSiendoEditada;
        if(tarjeta == null){
            // En caso de que no se pase 'tarjeta' por parámetro
            tarjetaSiendoEditada = new Tarjeta();
        }else{
            // Si se pasa 'tarjeta' por parámetro trabajamos con ella, la clonamos.
            tarjetaSiendoEditada = (Tarjeta) tarjeta.clone();
        }



        // CREACIÓN DE LOS CAMPOS DE FORMULARIO




        ComboBox<String> tipoTarjetaCombobox = new ComboBox<String>();
        tipoTarjetaCombobox.setId("tipoTarjeta");
        tipoTarjetaCombobox.setLabel("Tipo tarjeta");
        tipoTarjetaCombobox.setItems("Crédito", "Débito");
        tipoTarjetaCombobox.setAutofocus(true);
        tipoTarjetaCombobox.setWidth("300px");

        if(tarjeta == null){
            tipoTarjetaCombobox.setValue("Débito"); // En caso de que se pase un null por tarjeta
        }else{
            tipoTarjetaCombobox.setValue(tarjetaSiendoEditada.getTipoTarjeta()); // Si pasamos una tarjeta por parámetro, tomamos su valor
        }



        // Creo el textField para TipoTarjeta
//        TextField txtFdtipoTarjeta = new TextField();
//        if(tarjeta == null){
//            txtFdtipoTarjeta.setValue(""); // En caso de que se pase un null por tarjeta
//        }else{
//            txtFdtipoTarjeta.setValue(tarjetaSiendoEditada.getTipoTarjeta()); // Si pasamos una tarjeta por parámetro, tomamos su valor
//        }
//        txtFdtipoTarjeta.setValueChangeMode(ValueChangeMode.EAGER);




        // Creo el textField para NumeroTarjeta
        TextField txtFdNumeroTarjeta = new TextField();
        if(tarjeta == null){
            txtFdNumeroTarjeta.setValue("");
        }else{
            txtFdNumeroTarjeta.setValue(tarjetaSiendoEditada.getNumeroTarjeta());
        }
        txtFdNumeroTarjeta.setValueChangeMode(ValueChangeMode.EAGER);

        // Creo un DataPicker para la fecha de caducidad
        DatePicker datePickerFechaCaducidad = new DatePicker();
        LocalDate now = LocalDate.now();
        datePickerFechaCaducidad.setValue(now);

        // Creo un NumberField para el campo 'CVV'
        NumberField nmbFdCvv = new NumberField("CVV");
        nmbFdCvv.setValue(Math.floor(Math.random()*1000 - 1));
        nmbFdCvv.setMin(0);
        nmbFdCvv.setMax(999);

        // Campo para 'límite máximo' de la tarjeta
        NumberField nmbFdLimiteMaximo = new NumberField("Límite máximo");
        nmbFdLimiteMaximo.setValue(500d);

        // ComboBox para la Cuenta bancaria que asociada a la tarjeta
        ComboBox<Cuenta> cuentaCombobox = new ComboBox<Cuenta>();
        cuentaCombobox.setId("cuenta");
        cuentaCombobox.setItemLabelGenerator(Cuenta::getNumeroCuenta);
        cuentaCombobox.setLabel("Cuenta");
        cuentaCombobox.setItems(cuentaService.listadoCompletoCuentas());
        cuentaCombobox.setAutofocus(true);
        cuentaCombobox.setWidth("300px");

        // Le asignamos un valor inicial , dependiendo de la tarjeta pasada por parámetro
        if(tarjeta == null){
            cuentaCombobox.setValue(null);
        }else{
            cuentaCombobox.setValue(tarjetaSiendoEditada.getCuenta());
            cuentaCombobox.setReadOnly(true);
        }



        // Creamos el formulario
        VerticalLayout layoutEntrada = new VerticalLayout();

        layoutEntrada.add(new H4("Crear tarjeta"));

        Label infoLabel = new Label();
        NativeButton save = new NativeButton("Save");
        NativeButton reset = new NativeButton("Reset");

        layoutWithBinder.addFormItem(cuentaCombobox, "Cuenta asociada");


        layoutWithBinder.addFormItem(tipoTarjetaCombobox, "Tipo de tarjeta:");



        layoutWithBinder.addFormItem(txtFdNumeroTarjeta, "Número de tarjeta");
        layoutWithBinder.addFormItem(nmbFdLimiteMaximo, "Límite máximo");
        layoutWithBinder.addFormItem(datePickerFechaCaducidad, "Fecha de caducidad");
        layoutWithBinder.addFormItem(nmbFdCvv, "CVV");
        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, reset);
        save.getStyle().set("marginRight", "10px");





        // BINDING

        tipoTarjetaCombobox.setRequiredIndicatorVisible(true);
        binder.forField(tipoTarjetaCombobox)
                .withValidator(new StringLengthValidator(
                        "Por favor añadir el tipo de Tarjeta", 1, null))
                .bind(Tarjeta::getTipoTarjeta, Tarjeta::setTipoTarjeta);

        txtFdNumeroTarjeta.setRequiredIndicatorVisible(true);
        binder.forField(txtFdNumeroTarjeta)
                .withValidator(new StringLengthValidator(
                        "Por favor añadir el número de Tarjeta", 1, null))
                .bind(Tarjeta::getNumeroTarjeta, Tarjeta::setNumeroTarjeta);

        datePickerFechaCaducidad.setRequiredIndicatorVisible(true);
        binder.forField(datePickerFechaCaducidad)
                .withValidator(new DateRangeValidator("No puede ser anterior al día actual", now,null))
                .bind(Tarjeta::getFechaCaducidad, Tarjeta::setFechaCaducidad);


        if(tarjetaSiendoEditada.getCvv() != null)
            nmbFdCvv.setValue(tarjetaSiendoEditada.getCvv().doubleValue());
        nmbFdCvv.setRequiredIndicatorVisible(true);
//        binder.forField(nmbFdCvv)
//                .asRequired()
//                .bind( tarjeta1 -> tarjeta1.);

        nmbFdLimiteMaximo.setRequiredIndicatorVisible(true);
        binder.forField(nmbFdLimiteMaximo)
                .withValidator(new DoubleRangeValidator("Tiene que ser mayor a 500.0", 500.0, null))
                .bind(Tarjeta::getLimiteMaximo, Tarjeta::setLimiteMaximo);



//        binder.forField(cuentaCombobox);





        // Evento botón SAVE
        save.addClickListener(event -> {
            if (binder.writeBeanIfValid(tarjetaSiendoEditada)) {

                tarjetaSiendoEditada.setCvv(nmbFdCvv.getValue().intValue()); // asignamos el Cvv a la tarjeta a crear
                tarjetaSiendoEditada.setCuenta(cuentaCombobox.getValue());


                boolean creada = tarjetaSiendoEditada.getId() == null?true:false;

                if(tarjetaSiendoEditada.getCuenta() != null){
                    if(tarjetaService.crearTarjeta(tarjetaSiendoEditada) != null){
                        if(creada){
                            Notification.show("Tarjeta creada.", 2000, Notification.Position.MIDDLE);

                        }else{
                            Notification.show("Tarjeta actualizada.", 2000, Notification.Position.MIDDLE);
                        }

                        this.close();
                    }else{
                        infoLabel.setText(tarjetaSiendoEditada.getNumeroTarjeta() + ", ya existe");
//                    Notification.show("Esta categoria ya existe.", 2000, Notification.Position.MIDDLE);
                    }
                }else{
                    Notification.show("No asociada a ninguna cuenta", 2000, Notification.Position.MIDDLE);
                }



            } else {
                BinderValidationStatus<Tarjeta> validate = binder.validate();

                infoLabel.setText("Se ha producido un error");
            }
        });
        reset.addClickListener(event -> {

            binder.readBean(null);
            infoLabel.setText("");
        });
        add(layoutEntrada,layoutWithBinder, actions, infoLabel);



    }
}
