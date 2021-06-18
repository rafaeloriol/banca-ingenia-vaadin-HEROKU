package com.example.application.backend.servicebanca.impl;

import com.example.application.backend.repositorybanca.CategoriaRepository;
import com.example.application.backend.servicebanca.CategoriaService;
import com.example.application.backend.modelbanca.Categoria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    // inyección
    private final CategoriaRepository categoriaRepository;

    public CategoriaServiceImpl(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public Categoria crearCategoria(Categoria categoria) {
        // Si ya existe el tipo de Categoria devuelve null
        if(categoriaRepository.existsByTipoCategoria(categoria.getTipoCategoria()) == true) {
            return null;
        }else{
            return categoriaRepository.save(categoria);
        }

    }

    @Override
    public List<Categoria> encuentraCategorias() {
        return categoriaRepository.findAll();
    }

    @Override
    public void borrarCategoria(Categoria categoria) {
        categoriaRepository.deleteById(categoria.getId());
    }

    @Override
    public Categoria encuentraPorTipoCategoria(String tipoCategoria) {
        if(tipoCategoria.equals("")){
            return null;
        }else{
            Categoria categoriaResultado = categoriaRepository.findByTipoCategoria(tipoCategoria);
            return categoriaResultado;
        }
    }
}
