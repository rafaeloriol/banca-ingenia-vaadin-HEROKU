package com.example.application.backend.repositorybanca;



import com.example.application.backend.modelbanca.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    boolean existsById(Long id);


    Usuario getByUsername(String username);

    Usuario getByActivationCode(String activationCode);

}
