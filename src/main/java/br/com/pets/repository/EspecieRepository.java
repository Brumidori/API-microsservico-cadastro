package br.com.pets.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pets.model.Especie;
import br.com.pets.model.Pet;

public interface EspecieRepository extends JpaRepository<Especie, Integer>{
	
	Optional <Especie> findByNome(String nome);
	Boolean existsByNome(String nome);
	Boolean existsByIdNotAndNome(Integer id, String nome);
}
