package com.fiap.gs.api.service;

import com.fiap.gs.api.domain.Trilha;
import com.fiap.gs.api.dto.TrilhaRequest;
import com.fiap.gs.api.dto.TrilhaResponse;
import com.fiap.gs.api.repository.TrilhaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class TrilhaService {
    private final TrilhaRepository repo;

    public TrilhaService(TrilhaRepository repo) { this.repo = repo; }

    public TrilhaResponse create(TrilhaRequest req) {
        Trilha t = new Trilha();
        t.setNome(req.nome());
        t.setDescricao(req.descricao());
        Trilha saved = repo.save(t);
        return toResponse(saved);
    }

    public Page<TrilhaResponse> list(String nome, Pageable pageable) {
        Page<Trilha> page = (nome == null || nome.isBlank())
                ? repo.findAll(pageable)
                : repo.findByNomeContainingIgnoreCase(nome, pageable);
        return page.map(this::toResponse);
    }

    public TrilhaResponse get(Long id) {
        return toResponse(findOr404(id));
    }

    public TrilhaResponse update(Long id, TrilhaRequest req) {
        Trilha t = findOr404(id);
        t.setNome(req.nome());
        t.setDescricao(req.descricao());
        return toResponse(repo.save(t));
    }

    public void delete(Long id) {
        Trilha t = findOr404(id);
        repo.delete(t);
    }

    private Trilha findOr404(Long id) {
        return repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(NOT_FOUND, "Trilha não encontrada"));
    }

    private TrilhaResponse toResponse(Trilha t) {
        return new TrilhaResponse(t.getId(), t.getNome(), t.getDescricao(), t.getModulos().size());
    }
}
