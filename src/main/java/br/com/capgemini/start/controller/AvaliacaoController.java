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
import br.com.capgemini.start.model.Farol;
import br.com.capgemini.start.model.form.AvaliacaoForm;
import br.com.capgemini.start.model.form.AvaliacaoListaForm;
import br.com.capgemini.start.service.AvaliacaoService;

@RestController
@RequestMapping("avaliacao")
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class AvaliacaoController {

	private static final String FUNCIONALIDADE = "avaliacao";
	
	@Autowired
	private AvaliacaoService service;

	@Autowired
	private FormFactory formFactory;
	
	private ModelAndView form(AvaliacaoForm form) {
		return formFactory.newModelAndView(FUNCIONALIDADE + "/form")
				.addObject("form", form)
				.addObject("farois", Farol.valores());
	}
	
	@GetMapping
	@Transactional
	public ModelAndView pegar(@RequestParam(value="id", required = false) Long id) {
		return form(id != null ? service.form(id) : new AvaliacaoForm());
	}
	
	@PostMapping
	@Transactional
	public ModelAndView salvar(@ModelAttribute("form") @Valid AvaliacaoForm form, BindingResult result) {
		if(result.hasErrors()) {
			formFactory.setErro("Erro ao salvar Avaliação");
			
			return form(form);
		}
		
		service.salvar(form);
		
		formFactory.setSucesso("Avaliação salva com sucesso");
		
		// nao definido
		return form(new AvaliacaoForm());
	}
	
	@PostMapping("lista")
	@Transactional
	public ModelAndView salvar(@Valid AvaliacaoListaForm form) {
		service.salvar(form);
		
		formFactory.setSucesso("Avaliação salva com sucesso");
		
		return new ModelAndView("redirect:/" + form.getViewAnterior());
	}
}
