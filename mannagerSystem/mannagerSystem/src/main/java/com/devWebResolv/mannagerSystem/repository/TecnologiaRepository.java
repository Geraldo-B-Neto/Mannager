package com.devWebResolv.mannagerSystem.repository;

import com.devWebResolv.mannagerSystem.model.Tecnologia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TecnologiaRepository extends JpaRepository<Tecnologia, Long> {

    Optional<Tecnologia> findByNomeIgnoreCase(String nome);

    boolean existsByNomeIgnoreCase(String nome);
}
