package br.com.capgemini.start.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.capgemini.start.factory.FormFactory;
import br.com.capgemini.start.model.form.AlterarSenhaForm;
import br.com.capgemini.start.service.UsuarioService;
import br.com.capgemini.start.validation.UsuarioValidator;

@RestController
@RequestMapping("usuario")
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class UsuarioController {

	private static final String FUNCIONALIDADE = "usuario";
	
	@Autowired
	private UsuarioService service;
	
	@Autowired
	private UsuarioValidator validator;
	
	@Autowired
	private FormFactory formFactory;

	private ModelAndView alterarSenhaForm(AlterarSenhaForm form) {
		return formFactory.newModelAndView(FUNCIONALIDADE + "/alterar_senha_form")
				.addObject("form", form);
	}
	
	@GetMapping("alterar/senha")
	public ModelAndView alterarSenha() {
		return alterarSenhaForm(new AlterarSenhaForm());
	}
	
	@PostMapping("alterar/senha")
	@Transactional
	public ModelAndView alterarSenha(@ModelAttribute("form") @Valid AlterarSenhaForm form, BindingResult result) {
		validator.validaAlterarSenha(form, result);
		
		if(result.hasErrors()) {
			formFactory.setErro("Erro ao alterar senha");
			
			return alterarSenhaForm(form);
		}
		
		service.alterarSenha(form);
		
		formFactory.setSucesso("Senha alterada com sucesso");
		
		return new ModelAndView("redirect:/logout");
	}
}
