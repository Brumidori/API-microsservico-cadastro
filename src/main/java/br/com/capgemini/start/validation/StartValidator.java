package br.com.capgemini.start.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import br.com.capgemini.start.model.form.StartForm;
import br.com.capgemini.start.repository.CoachRepository;

@Component
public class StartValidator {
	
	@Autowired
	private UsuarioValidator usuarioValidator;
	
	@Autowired
	private CoachRepository coachRepository;
	
	public void validaSalvar(StartForm form, BindingResult result) {
		usuarioValidator.validaSalvar(form.getId(), form.getEmail(), result);
		
		if(
				(form.getIdCoach() != null && form.getIdGestor() != null && !coachRepository.existsByIdAndGestor_id(form.getIdCoach(), form.getIdGestor()))
				|| (form.getIdCoach() != null && form.getIdGestor() == null)
				){
			result.rejectValue("idCoach", "erro de banco de dados", new String[]{"idCoach"}, "O Coach escolhido tem que ter como gestor o Gestor escolhido");
		}
	}
}
