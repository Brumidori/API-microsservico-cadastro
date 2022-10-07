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

import br.com.capgemini.start.model.Farol;
import br.com.capgemini.start.model.Usuario;
import br.com.capgemini.start.model.form.AgendamentoEntrevistaForm;
import br.com.capgemini.start.model.form.EntrevistaNegocioForm;
import br.com.capgemini.start.service.EntrevistaNegocioService;

@RestController
@RequestMapping("entrevista/negocio")
public class EntrevistaNegocioController {

	private static final String FUNCIONALIDADE = "entrevista/negocio";
	
	@Autowired
	private EntrevistaNegocioService service;
	
	@Autowired
	private StartController startController;

	private ModelAndView form(EntrevistaNegocioForm form, String sucesso, String erro) {
		return new ModelAndView(FUNCIONALIDADE + "/form")
				.addObject("form", form)
				.addObject("farois", Farol.valores())
				.addObject("sucesso", sucesso)
				.addObject("erro", erro)
				.addObject("usuario", Usuario.usuarioLogado());
	}
	
	@GetMapping("agendamento")
	public ModelAndView agendamento(AgendamentoEntrevistaForm entrevistaForm) {
		EntrevistaNegocioForm form = service.entrevistaForm(entrevistaForm);
		
		return form(form, null, null);
	}
	
	@GetMapping
	public ModelAndView pegar(@RequestParam(value="idStart") Long idStart) {
		return form(service.form(idStart), null, null);
	}
	
	@PostMapping
	@Transactional
	public ModelAndView salvar(@ModelAttribute("form") @Valid EntrevistaNegocioForm form, BindingResult result) {
		if(result.hasErrors()) {
			return form(form, null, "Erro ao salvar Entrevista");
		}
		
		service.salvar(form);
		
		return startController.formLista("Entrevista salva com sucesso", null);
	}
}
