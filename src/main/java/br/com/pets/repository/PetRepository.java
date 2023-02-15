package br.com.pets.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pets.model.Pet;

public interface PetRepository extends JpaRepository<Pet, Integer>{
	Optional <Pet> existsByNome(String nome);
	Boolean existsByEspecie_id(Integer id);

}
