package br.com.pets.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pets.model.Especie;

public interface EspecieRepository extends JpaRepository<Especie, Integer>{
	
	Optional <Especie> findByNome(String nome);
}
