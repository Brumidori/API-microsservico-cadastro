package br.com.capgemini.start.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.capgemini.start.model.Gestor;

public interface GestorRepository extends JpaRepository<Gestor, Long> {

	List<Gestor> findAllByNome(String nome);
}
