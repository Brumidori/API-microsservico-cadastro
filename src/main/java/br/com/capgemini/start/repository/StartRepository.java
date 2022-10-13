package br.com.capgemini.start.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.capgemini.start.model.Start;

public interface StartRepository extends JpaRepository<Start, Long> , JpaSpecificationExecutor<Start>{

	List<Start> findAllByNome(String nome);
	
	List<Start> findAllByGestor_id(Long idGestor, Sort sort);
	
	List<Start> findAllByCoach_id(Long idCoach, Sort sort);
	
	List<Start> findAllByTurma_id(Long idTurma);
}
