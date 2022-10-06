package br.com.capgemini.start.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.capgemini.start.model.Agendamento;

public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {

	List<Agendamento> findByCoach_gestor_id(Long id, Sort sort);

	List<Agendamento> findByCoach_id(Long id, Sort sort);

}
