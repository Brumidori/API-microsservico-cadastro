package br.com.capgemini.start.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.capgemini.start.model.EntrevistaTecnica;

public interface EntrevistaTecnicaRepository extends JpaRepository<EntrevistaTecnica, Long> {

}
