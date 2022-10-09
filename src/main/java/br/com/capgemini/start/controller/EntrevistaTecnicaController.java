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
import br.com.capgemini.start.model.form.AgendamentoEntrevistaForm;
import br.com.capgemini.start.model.form.EntrevistaTecnicaForm;
import br.com.capgemini.start.service.EntrevistaTecnicaService;

@RestController
@RequestMapping("entrevista/tecnica")
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class EntrevistaTecnicaController {

	private static final String FUNCIONALIDADE = "entrevista/tecnica";
	
	@Autowired
	private EntrevistaTecnicaService service;
	
	@Autowired
	private FormFactory formFactory;
	
	private ModelAndView form(EntrevistaTecnicaForm form) {
		return formFactory.newModelAndView(FUNCIONALIDADE + "/form")
				.addObject("form", form)
				.addObject("farois", Farol.valores());
	}
	
	@GetMapping("agendamento")
	public ModelAndView agendamento(AgendamentoEntrevistaForm entrevistaForm) {
		EntrevistaTecnicaForm form = service.entrevistaForm(entrevistaForm);
		
		return form(form);
	}
	
	@GetMapping
	public ModelAndView pegar(@RequestParam(value="idStart") Long idStart) {
		return form(service.form(idStart));
	}
	
	@PostMapping
	@Transactional
	public ModelAndView salvar(@ModelAttribute("form") @Valid EntrevistaTecnicaForm form, BindingResult result) {
		if(result.hasErrors()) {
			formFactory.setErro("Erro ao salvar Entrevista");
			
			return form(form);
		}
		
		service.salvar(form);
		
		formFactory.setSucesso("Entrevista salva com sucesso");
		
		return new ModelAndView("redirect:/home");
	}
}
