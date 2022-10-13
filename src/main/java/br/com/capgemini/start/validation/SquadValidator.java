package br.com.capgemini.start.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import br.com.capgemini.start.model.form.SquadForm;
import br.com.capgemini.start.repository.SquadRepository;

@Component
public class SquadValidator {
	
	@Autowired
	private SquadRepository repository;
	
	public void validaSalvar(SquadForm form, BindingResult result) {
		if(form.getNome() != null) {
			if((form.getId() == null && repository.existsByNome(form.getNome())) || (form.getId() != null && repository.existsByIdNotAndNome(form.getId(), form.getNome()))){
				result.rejectValue("nome", "erro de banco de dados", new String[]{"nome"}, "nome já cadastrado");
			}
		}
		
		if(form.getNomeAbreviado() != null) {
			if((form.getId() == null && repository.existsByNomeAbreviado(form.getNomeAbreviado())) || (form.getId() != null && repository.existsByIdNotAndNomeAbreviado(form.getId(), form.getNomeAbreviado()))){
				result.rejectValue("nomeAbreviado", "erro de banco de dados", new String[]{"nomeAbreviado"}, "nome abreviado já cadastrado");
			}
		}		
	}
}
