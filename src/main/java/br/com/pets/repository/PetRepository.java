package br.com.pets.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pets.model.Pet;

public interface PetRepository extends JpaRepository<Pet, Integer>{

}
