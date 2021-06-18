package com.example.application.backend.servicebanca.impl;


import com.example.application.backend.modelbanca.Role;
import com.example.application.backend.modelbanca.Usuario;
import com.example.application.backend.repositorybanca.UserRepository;
import com.example.application.views.balance.BalanceView;
import com.example.application.views.inicio.InicioView;
import com.example.application.views.main.MainView;
import com.example.application.views.prestamo.PrestamoView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import com.example.application.views.cuentas.CuentasView;

import com.example.application.views.movimientos.MovimientosView;
import com.example.application.views.tarjetas.TarjetasView;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthService {

    public record AuthorizedRoute(String route, String name, Class<? extends Component> view) {

    }

    public class AuthException extends Exception {

    }

    private final UserRepository userRepository;


    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private Usuario usuarioLogeado;
    public Usuario recuperaUsuarioLogeado(){
        return usuarioLogeado;
    }


    public void authenticate(String username, String password) throws AuthException {
        Usuario user = userRepository.getByUsername(username);
        usuarioLogeado = user;
        if (user != null && user.checkPassword(password)/* && user.isActive()*/) {
            VaadinSession.getCurrent().setAttribute(Usuario.class, user);
            createRoutes(user.getRole());
        } else {
            throw new AuthException();
        }
    }

    private void createRoutes(Role role) {
        getAuthorizedRoutes(role).stream()
                .forEach(route ->
                        RouteConfiguration.forSessionScope().setRoute(
                                route.route, route.view, MainView.class));
    }

    public List<AuthorizedRoute> getAuthorizedRoutes(Role role) {
        var routes = new ArrayList<AuthorizedRoute>();

        if (role.equals(Role.USER)) {
            routes.add(new AuthorizedRoute("inicio", "Inicio", InicioView.class));
            routes.add(new AuthorizedRoute("cuentas", "Cuentas", CuentasView.class));
            routes.add(new AuthorizedRoute("tarjetas", "Tarjetas", TarjetasView.class));
            routes.add(new AuthorizedRoute("movimientos", "Movimientos", MovimientosView.class));
            routes.add(new AuthorizedRoute("balance", "Balance", BalanceView.class));
            routes.add(new AuthorizedRoute("prestamo", "Prestamo", PrestamoView.class));

        } else if (role.equals(Role.ADMIN)) {
            routes.add(new AuthorizedRoute("inicio", "Inicio", InicioView.class));
            routes.add(new AuthorizedRoute("cuentas", "Cuentas", CuentasView.class));
            routes.add(new AuthorizedRoute("tarjetas", "Tarjetas", TarjetasView.class));
            routes.add(new AuthorizedRoute("movimientos", "Movimientos", MovimientosView.class));
            routes.add(new AuthorizedRoute("balance", "Balance", BalanceView.class));
            routes.add(new AuthorizedRoute("prestamo", "Prestamo", PrestamoView.class));
        }

        return routes;
    }

    public void register(String fullName, String email, String username, String password) {
        Usuario user = userRepository.save(new Usuario(fullName, email, username, password, Role.USER));
//        String text = "http://localhost:8080/activate?code=" + user.getActivationCode();
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("noreply@example.com");
//        message.setSubject("Confirmation email");
//        message.setText(text);
//        message.setTo(email);
//        mailSender.send(message);

        UI.getCurrent().navigate("login");
    }

    public void activate(String activationCode) throws AuthException {
        Usuario user = userRepository.getByActivationCode(activationCode);
        if (user != null) {
            user.setActive(true);
            userRepository.save(user);
        } else {
            throw new AuthException();
        }
    }

}
