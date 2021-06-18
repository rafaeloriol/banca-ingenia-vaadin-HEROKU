package com.example.application.views.login;


import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.example.application.backend.servicebanca.impl.AuthService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Route("register")
public class RegisterView extends VerticalLayout {

    private final AuthService authService;

    public RegisterView(AuthService authService) {
        this.authService = authService;

        setPadding(false);
//        setHeightFull();
//        setJustifyContentMode(JustifyContentMode.CENTER);
//        setAlignItems(Alignment.CENTER);

        add(registerComponent());
    }


    public Component registerComponent() {

        HorizontalLayout layoutCabecera = new HorizontalLayout();
        layoutCabecera.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutCabecera.getStyle().set("padding","30px");
        layoutCabecera.getStyle().set("margin", "0px 0px");

        Image logotipo = new Image("images/logo-banca-ingenia.PNG", "Banca Ingenia");
        logotipo.setHeight("50px");
        logotipo.setWidth("50px");

        H1 hTitulo = new H1("Ingenia Bank");
        hTitulo.getStyle().set("margin", "0px 0px 0px 10px");

        layoutCabecera.add(logotipo, hTitulo);
        this.setHorizontalComponentAlignment(Alignment.CENTER, layoutCabecera);

//        Image logo = new Image("images/logo-banca-ingenia.PNG", "Banca Ingenia");
//        H3 tittle = new H3("Sign up");
        TextField fullName = new TextField("Full name");
        TextField email = new TextField("Email");
        TextField username = new TextField("Username");
        PasswordField password1 = new PasswordField("Password");
        PasswordField password2 = new PasswordField("Confirm password");
        Button registerButton = new Button("Submit", event -> register(
                fullName.getValue(),
                email.getValue(),
                username.getValue(),
                password1.getValue(),
                password2.getValue()
        ));

        VerticalLayout registerLayout = new VerticalLayout();

        registerLayout.add(layoutCabecera, fullName, email, username, password1, password2, registerButton);
        registerLayout.setHeightFull();
        registerLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        registerLayout.setAlignItems(Alignment.CENTER);


        return registerLayout;
    }

    public void register(String fullName, String email, String username, String password1, String password2) {
        if (username.trim().isEmpty()) {
            Notification.show("Enter a username",2000, Notification.Position.MIDDLE);
        } else if (password1.isEmpty()) {
            Notification.show("Enter a password",2000, Notification.Position.MIDDLE);
        } else if (!password1.equals(password2)) {
            Notification.show("Passwords don't match",2000, Notification.Position.MIDDLE);
        } else if (fullName.trim().isEmpty()){
            Notification.show("Enter a Full Name",2000, Notification.Position.MIDDLE);
        }else if(invalidEmail(email)) {
            Notification.show("Invalid email",2000, Notification.Position.MIDDLE);
        }else {
            authService.register(fullName, email, username, password1);
            UI.getCurrent().getPage().setLocation("login");
        }
    }

    private boolean invalidEmail(String email) {

        // Patrón para validar el email
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");


        Matcher mather = pattern.matcher(email);

        if (mather.find() == true) {
            return false;
        } else {
            return true;
        }



    }
}
