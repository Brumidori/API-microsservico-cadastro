package br.com.pets.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pets.model.Especie;
import br.com.pets.repository.EspecieRepository;

@Component
public class EspecieValidator {
	
	@Autowired
	EspecieRepository repository;
	
	public boolean validar(Especie especie)
	{
		if(repository.findByNome(especie.getNome()).isPresent()){
			return true;
		}
		return false;
	}
}
