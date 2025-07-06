package com.upc.widegreenapi.serviceImpl;

import com.upc.widegreenapi.dtos.CategoriaConteoDTO;
import com.upc.widegreenapi.repositories.PublicacionCategoriaRepository;
import com.upc.widegreenapi.service.CategoriaConteoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoriaConteoServiceImpl implements CategoriaConteoService {

    @Autowired
    private PublicacionCategoriaRepository repo;

    @Override
    public List<CategoriaConteoDTO> cantidadPublicacionesPorCategoria() {
        List<Object[]> resultados = repo.cantidadPublicacionesPorCategoria();
        List<CategoriaConteoDTO> lista = new ArrayList<>();
        for (Object[] fila : resultados) {
            lista.add(new CategoriaConteoDTO((String) fila[0], ((Long) fila[1]).intValue()));
        }
        return lista;
    }
}
