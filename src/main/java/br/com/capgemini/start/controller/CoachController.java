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
import br.com.capgemini.start.model.Atuacao;
import br.com.capgemini.start.model.form.CoachForm;
import br.com.capgemini.start.service.CoachService;
import br.com.capgemini.start.service.GestorService;
import br.com.capgemini.start.validation.UsuarioValidator;

@RestController
@RequestMapping("coach")
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class CoachController {

	private static final String FUNCIONALIDADE = "coach";
	
	@Autowired
	private CoachService service;
	
	@Autowired
	private GestorService gestorService;
	
	@Autowired
	private UsuarioValidator usuarioValidator;

	@Autowired
	private FormFactory formFactory;
	
	private ModelAndView form(CoachForm form) {
		return formFactory.newModelAndView(FUNCIONALIDADE + "/form")
				.addObject("form", form)
				.addObject("atuacoes", Atuacao.values())
				.addObject("gestores", gestorService.listar());
	}
	
	private ModelAndView formLista() {
		return formFactory.newModelAndView(FUNCIONALIDADE + "/lista")
				.addObject("lista", service.listar());
	}
	
	private CoachForm model() {
		CoachForm model = new CoachForm();
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
	public ModelAndView salvar(@ModelAttribute("form") @Valid CoachForm form, BindingResult result) {
		usuarioValidator.validaSalvar(form.getId(), form.getEmail(), result);
		
		if(result.hasErrors()) {
			formFactory.setErro("Erro ao salvar Coach");
			
			return form(form);
		}
		
		service.salvar(form);
		
		formFactory.setSucesso("Coach salvo com sucesso");
		
		return new ModelAndView("redirect:/coach");
	}

	@GetMapping("listar")
	@Transactional
	public ModelAndView listar() {
		return formLista();
	}
}
