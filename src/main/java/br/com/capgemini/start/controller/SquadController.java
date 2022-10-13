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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.capgemini.start.factory.FormFactory;
import br.com.capgemini.start.model.form.SquadForm;
import br.com.capgemini.start.service.SquadService;
import br.com.capgemini.start.validation.SquadValidator;

@RestController
@RequestMapping("squad")
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class SquadController {

	private static final String FUNCIONALIDADE = "squad";
	
	@Autowired
	private SquadService service;
	
	@Autowired
	private SquadValidator validator;
	
	@Autowired
	private FormFactory formFactory;

	private ModelAndView form(SquadForm form) {
		return formFactory.newModelAndView(FUNCIONALIDADE + "/form")
				.addObject("form", form);
	}
	
	private ModelAndView formLista() {
		return formFactory.newModelAndView(FUNCIONALIDADE + "/lista")
				.addObject("lista", service.listar());
	}
	
	@GetMapping
	@Transactional
	public ModelAndView pegar(@RequestParam(value="id", required = false) Long id) {
		return form(id != null ? service.form(id) : new SquadForm());
	}
	
	@PostMapping
	@Transactional
	public ModelAndView salvar(@ModelAttribute("form") @Valid SquadForm form, BindingResult result) {
		validator.validaSalvar(form, result);
		
		if(result.hasErrors()) {
			formFactory.setErro("Erro ao salvar Squad");
			
			return form(form);
		}
		
		service.salvar(form);
		
		formFactory.setSucesso("Squad salva com sucesso");
		
		return new ModelAndView("redirect:/squad");
	}

	@GetMapping("listar")
	@Transactional
	public ModelAndView listar() {
		return formLista();
	}
}
