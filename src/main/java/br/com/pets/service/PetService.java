package br.com.pets.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pets.model.Pet;
import br.com.pets.repository.PetRepository;

@Service
public class PetService {

	@Autowired
	private PetRepository repository;

	public Pet pegar(Integer id) {
		return repository.findById(id).get();
	}

	public void salvar(Pet model) {
		model.setTelDono(model.getTelDono().replaceAll("\\D", ""));
		repository.save(model);
	}

	public void excluir(Integer id) {
		repository.deleteById(id);
	}

	public List<Pet> listar() {
		return repository.findAll();
	}
}
