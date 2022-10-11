package br.com.capgemini.start.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import br.com.capgemini.start.model.form.TurmaForm;
import br.com.capgemini.start.repository.TurmaRepository;

@Component
public class TurmaValidator {
	
	@Autowired
	private TurmaRepository repository;
	
	public void validaSalvar(TurmaForm form, BindingResult result) {
		if(form.getNome() == null) {
			return; // vai dar erro
		}
		
		if((form.getId() == null && repository.existsByNome(form.getNome())) || (form.getId() != null && repository.existsByIdNotAndNome(form.getId(), form.getNome()))){
			result.rejectValue("nome", "erro de banco de dados", new String[]{"nome"}, "nome já cadastrado");
		}
	}
}
