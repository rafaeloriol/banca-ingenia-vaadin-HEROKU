package com.example.application.views.categoria;

import com.example.application.backend.modelbanca.Categoria;
import com.example.application.backend.servicebanca.CategoriaService;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.BinderValidationStatus;
import com.vaadin.flow.data.validator.StringLengthValidator;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CategoriaForm extends Dialog {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CategoriaService categoriaService;


    public CategoriaForm(Categoria categoria, CategoriaService categoriaService) {
        this.categoriaService = categoriaService;

        FormLayout layoutWithBinder = new FormLayout();
        Binder<Categoria> binder = new Binder<>();

        // Entidad Categoria que se va a bindear
        Categoria categoriaSiendoEditada;
        if(categoria == null){
            categoriaSiendoEditada = new Categoria();
        }else{
            categoriaSiendoEditada = (Categoria) categoria.clone();
        }

        // Creo el textField
        TextField txtFdtipoCategoria = new TextField();
        if(categoria == null){
            txtFdtipoCategoria.setValue("");
        }else{
            txtFdtipoCategoria.setValue(categoriaSiendoEditada.getTipoCategoria());
        }
        txtFdtipoCategoria.setValueChangeMode(ValueChangeMode.EAGER);

        VerticalLayout layoutEntrada = new VerticalLayout();

        layoutEntrada.add(new H4("Crear categoria"),txtFdtipoCategoria);

        Label infoLabel = new Label();
        NativeButton save = new NativeButton("Save");
        NativeButton reset = new NativeButton("Reset");

        layoutWithBinder.addFormItem(txtFdtipoCategoria, "Tipo de categoria:");

        HorizontalLayout actions = new HorizontalLayout();
        actions.add(save, reset);
        save.getStyle().set("marginRight", "10px");


        txtFdtipoCategoria.setRequiredIndicatorVisible(true);
        binder.forField(txtFdtipoCategoria)
                .withValidator(new StringLengthValidator(
                        "Por favor añadir el tipo de Categoria", 1, null))
                .bind(Categoria::getTipoCategoria, Categoria::setTipoCategoria);



        save.addClickListener(event -> {
            if (binder.writeBeanIfValid(categoriaSiendoEditada)) {

                if(categoriaService.crearCategoria(categoriaSiendoEditada) != null){
                    Notification.show("Categoria creada.", 2000, Notification.Position.MIDDLE);

                    this.close();
                }else{
                    infoLabel.setText(categoriaSiendoEditada.getTipoCategoria() + ", ya existe");
//                    Notification.show("Esta categoria ya existe.", 2000, Notification.Position.MIDDLE);
                }

            } else {
                BinderValidationStatus<Categoria> validate = binder.validate();

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
