package br.com.capgemini.start.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.capgemini.start.model.Turma;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
	
	boolean existsByNome(String nome);

	boolean existsByIdNotAndNome(Long id, String nome);
}
