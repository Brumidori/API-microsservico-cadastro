package br.com.capgemini.start.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.capgemini.start.model.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {

	List<Avaliacao> findByIdStart(Long idStart, Sort sort);
}
