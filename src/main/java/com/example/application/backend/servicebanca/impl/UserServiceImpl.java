package com.example.application.backend.servicebanca.impl;


import com.example.application.backend.modelbanca.Usuario;
import com.example.application.backend.repositorybanca.UserRepository;
import com.example.application.backend.servicebanca.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    // Inyección del repositorio userRepository
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean existeUserConId(Long id) {
        return userRepository.existsById(id);
    }

    @Override
    public Usuario crearUsuario(Usuario usuario) {
        if(usuario.getId() == null){
            return userRepository.save(usuario);
        }else{
            return null;
        }
    }

    @Override
    public List<Usuario> encotrarTodosUsuarios() {
        return userRepository.findAll();
    }
}
