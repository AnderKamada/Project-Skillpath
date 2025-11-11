package com.fiap.gs.api.repository;

import com.fiap.gs.api.domain.Trilha;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrilhaRepository extends JpaRepository<Trilha, Long> {
    Page<Trilha> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
