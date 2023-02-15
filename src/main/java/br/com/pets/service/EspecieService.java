package br.com.pets.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.pets.model.Especie;
import br.com.pets.repository.EspecieRepository;

@Service
public class EspecieService {

	@Autowired
	EspecieRepository repository;
	
	public Especie pegar(Integer id) {
		return repository.findById(id).get();
	}
	
	public void salvar(Especie especie) {
		repository.save(especie);
	}
	
	public List<Especie> listar() {
		return repository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
	}
	
	public void excluir(Integer id) {
		repository.deleteById(id);
	}
}
