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

import br.com.capgemini.start.exception.ViewException;
import br.com.capgemini.start.factory.FormFactory;
import br.com.capgemini.start.model.form.AgendamentoEntrevistaForm;
import br.com.capgemini.start.service.AgendamentoService;
import br.com.capgemini.start.service.CoachService;

@RestController
@RequestMapping("agendamento")
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class AgendamentoController {

	private static final String FUNCIONALIDADE = "agendamento";
	
	@Autowired
	private AgendamentoService service;
	
	@Autowired
	private CoachService coachService;

	@Autowired
	private FormFactory formFactory;
	
	private ModelAndView form(AgendamentoEntrevistaForm form, String nomeForm) {
		return formFactory.newModelAndView(FUNCIONALIDADE + "/" + nomeForm)
				.addObject("form", form)
				.addObject("coachs", coachService.listar());
	}
	
	@GetMapping("entrevista/negocio")
	public ModelAndView pegarEntrevistaNegocio(@RequestParam(value="idStart") Long idStart, @RequestParam(value="viewAnterior") String viewAnterior) {
		AgendamentoEntrevistaForm form = new AgendamentoEntrevistaForm();
		form.setIdStart(idStart);
		form.setViewAnterior(viewAnterior);
		
		return form(form, "form_entrevista_negocio");
	}
	
	@PostMapping("entrevista/negocio")
	@Transactional
	public ModelAndView salvarEntrevistaNegocio(@ModelAttribute("form") @Valid AgendamentoEntrevistaForm form, BindingResult result) {
		if(result.hasErrors()) {
			formFactory.setErro("Erro ao criar Agendamento");
			
			return form(form, "form_entrevista_negocio");
		}
		
		service.salvarNegocio(form);
		
		formFactory.setSucesso("Agendamento criado com sucesso");
		
		return new ModelAndView("redirect:/" + form.getViewAnterior());
	}
	
	@GetMapping("entrevista/tecnica")
	public ModelAndView pegarEntrevistaTecnica(@RequestParam(value="idStart") Long idStart, @RequestParam(value="viewAnterior") String viewAnterior) {
		AgendamentoEntrevistaForm form = new AgendamentoEntrevistaForm();
		form.setIdStart(idStart);
		form.setViewAnterior(viewAnterior);
		
		return form(form, "form_entrevista_tecnica");
	}
	
	@PostMapping("entrevista/tecnica")
	@Transactional
	public ModelAndView salvarEntrevistaTecnica(@ModelAttribute("form") @Valid AgendamentoEntrevistaForm form, BindingResult result) {
		if(result.hasErrors()) {
			formFactory.setErro("Erro ao criar Agendamento");
			
			return form(form, "form_entrevista_tecnica");
		}
		
		service.salvarTecnica(form);
		
		formFactory.setSucesso("Agendamento criado com sucesso");
		
		return new ModelAndView("redirect:/" + form.getViewAnterior());
	}

	@GetMapping("excluir")
	@Transactional
	public ModelAndView excluir(@RequestParam("id") Long id) {
		try {
			service.excluir(id);
			
			formFactory.setSucesso("Agendamento excluído com sucesso");
			
			return new ModelAndView("redirect:/home");
		} catch (ViewException e) {
			formFactory.setErro("Agendamento não pode ser excluído, motivo(s): "+e.getErro());
			
			return new ModelAndView("redirect:/home");
		}
	}
}
