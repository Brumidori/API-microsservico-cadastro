package br.com.capgemini.start.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.capgemini.start.model.Coach;

public interface CoachRepository extends JpaRepository<Coach, Long> {

	List<Coach> findAllByNome(String nome);

	List<Coach> findByGestor_id(Long idGestor, Sort sort);

	boolean existsByIdAndGestor_id(Long id, Long idGestor);
}
