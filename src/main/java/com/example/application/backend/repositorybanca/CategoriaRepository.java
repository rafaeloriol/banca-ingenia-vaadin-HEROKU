package com.example.application.backend.repositorybanca;


import com.example.application.backend.modelbanca.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Boolean existsByTipoCategoria(String tipoCategoria);

    Categoria findByTipoCategoria(String tipoCategoria);

}
