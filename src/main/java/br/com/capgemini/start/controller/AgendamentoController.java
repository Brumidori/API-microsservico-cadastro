package br.com.capgemini.start.controller;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.capgemini.start.exception.ViewException;
import br.com.capgemini.start.model.Usuario;
import br.com.capgemini.start.model.form.AgendamentoEntrevistaForm;
import br.com.capgemini.start.service.AgendamentoService;
import br.com.capgemini.start.service.CoachService;

@RestController
@RequestMapping("agendamento")
public class AgendamentoController {

	private static final String FUNCIONALIDADE = "agendamento";
	
	@Autowired
	private AgendamentoService service;
	
	@Autowired
	private HomeController homeController;
	
	@Autowired
	private CoachService coachService;

	private ModelAndView form(AgendamentoEntrevistaForm form, String sucesso, String erro, String nomeForm) {
		return new ModelAndView(FUNCIONALIDADE + "/" + nomeForm)
				.addObject("form", form)
				.addObject("coachs", coachService.listar())
				.addObject("sucesso", sucesso)
				.addObject("erro", erro)
				.addObject("usuario", Usuario.usuarioLogado());
	}
	
	@GetMapping("entrevista/negocio")
	public ModelAndView pegarEntrevistaNegocio(@RequestParam(value="idStart") Long idStart) {
		AgendamentoEntrevistaForm form = new AgendamentoEntrevistaForm();
		form.setIdStart(idStart);
		
		return form(form, null, null, "form_entrevista_negocio");
	}
	
	@PostMapping("entrevista/negocio")
	@Transactional
	public ModelAndView salvarEntrevistaNegocio(@ModelAttribute("form") @Valid AgendamentoEntrevistaForm form, BindingResult result) {
		if(result.hasErrors()) {
			return form(form, null, "Erro ao salvar Agendamento", "form_entrevista_negocio");
		}
		
		service.salvarNegocio(form);
		
		return form(new AgendamentoEntrevistaForm(), "Agendamento salvo com sucesso", null, "form_entrevista_negocio");
	}
	
	@GetMapping("entrevista/tecnica")
	public ModelAndView pegarEntrevistaTecnica(@RequestParam(value="idStart") Long idStart) {
		AgendamentoEntrevistaForm form = new AgendamentoEntrevistaForm();
		form.setIdStart(idStart);
		
		return form(form, null, null, "form_entrevista_tecnica");
	}
	
	@PostMapping("entrevista/tecnica")
	@Transactional
	public ModelAndView salvarEntrevistaTecnica(@ModelAttribute("form") @Valid AgendamentoEntrevistaForm form, BindingResult result) {
		if(result.hasErrors()) {
			return form(form, null, "Erro ao salvar Agendamento", "form_entrevista_tecnica");
		}
		
		service.salvarTecnica(form);
		
		return form(new AgendamentoEntrevistaForm(), "Agendamento salvo com sucesso", null, "form_entrevista_tecnica");
	}

	@GetMapping("excluir")
	@Transactional
	public ModelAndView excluir(@RequestParam("id") Long id) {
		try {
			service.excluir(id);
			return homeController.form("Agendamento excluído com sucesso", null);
		} catch (ViewException e) {
			return homeController.form(null, "Agendamento não pode ser excluído, motivo(s): "+e.getErro());
		}
	}
}
