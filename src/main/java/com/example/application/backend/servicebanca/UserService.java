package com.example.application.backend.servicebanca;


import com.example.application.backend.modelbanca.Usuario;

import java.util.List;

public interface UserService {

    // Comprueba si un user existe por su Id
    boolean existeUserConId(Long id);

    // Crear un usuarios
    Usuario crearUsuario(Usuario usuario);

    // Recuperar todos los usuarios
    List<Usuario> encotrarTodosUsuarios();
}
