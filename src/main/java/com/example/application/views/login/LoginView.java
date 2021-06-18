package com.example.application.views.login;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.router.RouterLink;
import com.example.application.backend.servicebanca.impl.AuthService;

@RouteAlias(value="")
@Route(value = "login")
@PageTitle("Login")
//@CssImport("./styles/views/login/login-view.css")
public class LoginView extends VerticalLayout {

    public LoginView(AuthService authService) {

        setPadding(false);
        setJustifyContentMode(JustifyContentMode.START);
        setAlignItems(Alignment.START);


        HorizontalLayout layoutCabecera = new HorizontalLayout();
        layoutCabecera.setJustifyContentMode(JustifyContentMode.CENTER);
        layoutCabecera.getStyle().set("padding","30px");

        Image logotipo = new Image("images/logo-banca-ingenia.PNG", "Banca Ingenia");
        logotipo.setHeight("50px");
        logotipo.setWidth("50px");

        H1 hTitulo = new H1("Ingenia Bank");
        hTitulo.getStyle().set("margin", "0px 0px 0px 10px");

        layoutCabecera.add(logotipo, hTitulo);
        this.setHorizontalComponentAlignment(Alignment.CENTER, layoutCabecera);









        setId("login-view");
        var username = new TextField("Username");
        var password = new PasswordField("Password");

        this.setHorizontalComponentAlignment(Alignment.CENTER, username);
        this.setHorizontalComponentAlignment(Alignment.CENTER, password);

        Button btnLogin = new Button("Login");
        this.setHorizontalComponentAlignment(Alignment.CENTER,btnLogin);
        btnLogin.addClickListener(event -> {
            try {
                authService.authenticate(username.getValue(), password.getValue());
                UI.getCurrent().navigate("inicio");
            } catch (AuthService.AuthException e) {
                Notification.show("Credenciales erróneas");
            }
        });

        RouterLink linkRegistro = new RouterLink("Registro", RegisterView.class);
        this.setHorizontalComponentAlignment(Alignment.CENTER, linkRegistro);

        add(layoutCabecera, username, password, btnLogin, linkRegistro);





    }

}
