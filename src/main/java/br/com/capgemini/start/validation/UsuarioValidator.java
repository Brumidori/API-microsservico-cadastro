package br.com.capgemini.start.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import br.com.capgemini.start.model.form.AlterarSenhaForm;
import br.com.capgemini.start.repository.UsuarioRepository;

@Component
public class UsuarioValidator {
	
	@Autowired
	private UsuarioRepository repository;
	
	public void validaSalvar(Long id, String email, BindingResult result) {
		if(email == null) {
			return; // vai dar erro
		}
		
		if((id == null && repository.existsByEmail(email)) || (id != null && repository.existsByIdNotAndEmail(id, email))){
			result.rejectValue("email", "erro de banco de dados", new String[]{"email"}, "email já cadastrado");
		}
	}

	public void validaAlterarSenha(AlterarSenhaForm form, BindingResult result) {
		if(form.getSenha() != null && form.getSenhaConfirma() != null && !form.getSenha().equals(form.getSenhaConfirma())){
			result.rejectValue("senhaConfirma", "erro de negócio", new String[]{"senhaConfirma"}, "as senhas devem se iguais");
		}
	}
}
