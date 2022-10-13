package br.com.capgemini.start.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.capgemini.start.model.Squad;

public interface SquadRepository extends JpaRepository<Squad, Long> {
	
	boolean existsByNome(String nome);

	boolean existsByIdNotAndNome(Long id, String nome);
	
	boolean existsByNomeAbreviado(String nomeAbreviado);

	boolean existsByIdNotAndNomeAbreviado(Long id, String nomeAbreviado);
}
