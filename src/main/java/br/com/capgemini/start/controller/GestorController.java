package br.com.capgemini.start.controller;

import java.time.LocalDate;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.capgemini.start.factory.FormFactory;
import br.com.capgemini.start.model.CorProjeto;
import br.com.capgemini.start.model.form.GestorForm;
import br.com.capgemini.start.service.GestorService;
import br.com.capgemini.start.validation.UsuarioValidator;

@RestController
@RequestMapping("gestor")
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class GestorController {

	private static final String FUNCIONALIDADE = "gestor";
	
	@Autowired
	private GestorService service;
	
	@Autowired
	private UsuarioValidator usuarioValidator;

	@Autowired
	private FormFactory formFactory;
	
	private ModelAndView form(GestorForm form) {
		return formFactory.newModelAndView(FUNCIONALIDADE + "/form")
				.addObject("form", form)
				.addObject("cores", CorProjeto.valores());
	}
	
	private ModelAndView formLista() {
		return formFactory.newModelAndView(FUNCIONALIDADE + "/lista")
				.addObject("lista", service.listar());
	}
	
	private GestorForm model() {
		GestorForm model = new GestorForm();
		model.setDataCadastro(LocalDate.now());
		return model;
	}
	
	@GetMapping
	@Transactional
	public ModelAndView pegar(@RequestParam(value="id", required = false) Long id) {
		return form(id != null ? service.form(id) : model());
	}
	
	@PostMapping
	@Transactional
	public ModelAndView salvar(@ModelAttribute("form") @Valid GestorForm form, BindingResult result) {
		usuarioValidator.validaSalvar(form.getId(), form.getEmail(), result);
		
		if(result.hasErrors()) {
			formFactory.setErro("Erro ao salvar Gestor");
			
			return form(form);
		}
		
		service.salvar(form);
		
		formFactory.setSucesso("Gestor salvo com sucesso");
		
		return new ModelAndView("redirect:/gestor");
	}

	@GetMapping("listar")
	@Transactional
	public ModelAndView listar() {
		return formLista();
	}
}
