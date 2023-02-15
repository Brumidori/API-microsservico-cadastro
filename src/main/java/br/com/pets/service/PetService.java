package br.com.pets.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.pets.model.Pet;
import br.com.pets.repository.PetRepository;

@Service
public class PetService {

	@Autowired
	PetRepository repository;
	
	public Pet pegar(Integer id) {
		return repository.findById(id).get();
	}
	
	public void salvar(Pet model) {
		repository.save(model);
	}
	
	public List<Pet> listar() {
		return repository.findAll(Sort.by(Sort.Direction.ASC,"especie.nome", "nome"));
	}
	
	public void excluir(Integer id) {
		repository.deleteById(id);
	}
}
