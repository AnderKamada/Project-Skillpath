package com.fiap.gs.api.service;

import com.fiap.gs.api.domain.Modulo;
import com.fiap.gs.api.domain.Trilha;
import com.fiap.gs.api.dto.ModuloRequest;
import com.fiap.gs.api.dto.ModuloResponse;
import com.fiap.gs.api.repository.ModuloRepository;
import com.fiap.gs.api.repository.TrilhaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ModuloService {
    private final ModuloRepository moduloRepo;
    private final TrilhaRepository trilhaRepo;

    public ModuloService(ModuloRepository moduloRepo, TrilhaRepository trilhaRepo) {
        this.moduloRepo = moduloRepo;
        this.trilhaRepo = trilhaRepo;
    }

    public ModuloResponse create(ModuloRequest req) {
        Trilha trilha = trilhaRepo.findById(req.trilhaId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Trilha não encontrada"));

        Modulo m = new Modulo();
        m.setTitulo(req.titulo());
        m.setTrilha(trilha);

        Modulo saved = moduloRepo.save(m);
        return new ModuloResponse(saved.getId(), trilha.getId(), saved.getTitulo());
    }

    public ModuloResponse get(Long id) {
        Modulo m = moduloRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Módulo não encontrado"));
        return new ModuloResponse(m.getId(), m.getTrilha().getId(), m.getTitulo());
    }

    public ModuloResponse update(Long id, ModuloRequest req) {
        Modulo m = moduloRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Módulo não encontrado"));
        Trilha trilha = trilhaRepo.findById(req.trilhaId())
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Trilha não encontrada"));
        m.setTitulo(req.titulo());
        m.setTrilha(trilha);
        Modulo saved = moduloRepo.save(m);
        return new ModuloResponse(saved.getId(), trilha.getId(), saved.getTitulo());
    }

    public void delete(Long id) {
        Modulo m = moduloRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Módulo não encontrado"));
        moduloRepo.delete(m);
    }
}
