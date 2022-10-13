package br.com.capgemini.start.controller;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.capgemini.start.factory.FormFactory;
import br.com.capgemini.start.model.Atuacao;
import br.com.capgemini.start.model.Farol;
import br.com.capgemini.start.model.dto.StartRelatorioDto;
import br.com.capgemini.start.model.form.ListaStartForm;
import br.com.capgemini.start.model.form.StartForm;
import br.com.capgemini.start.service.CoachService;
import br.com.capgemini.start.service.GestorService;
import br.com.capgemini.start.service.SquadService;
import br.com.capgemini.start.service.StartService;
import br.com.capgemini.start.service.TurmaService;
import br.com.capgemini.start.validation.StartValidator;

@RestController
@RequestMapping("start")
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class StartController {

	private static final String FUNCIONALIDADE = "start";
	
	@Autowired
	private StartService service;
	
	@Autowired
	private CoachService coachService;
	
	@Autowired
	private GestorService gestorService;
	
	@Autowired
	private TurmaService turmaService;
	
	@Autowired
	private SquadService squadService;
	
	@Autowired
	private StartValidator validator;

	@Autowired
	private FormFactory formFactory;
	
	private ModelAndView form(StartForm form) {
		return formFactory.newModelAndView(FUNCIONALIDADE + "/form")
				.addObject("form", form)
				.addObject("atuacoes", Atuacao.values())
				.addObject("gestores", gestorService.listar())
				.addObject("coachs", form.getIdGestor() != null ? coachService.listarGestor(form.getIdGestor()) : new ArrayList<>())
				.addObject("turmas", turmaService.listar())
				.addObject("squads", squadService.listar());
	}
	
	private ModelAndView formLista(ListaStartForm form) {
		return formFactory.newModelAndView(FUNCIONALIDADE + "/lista")
				.addObject("form", form)
				.addObject("lista", service.listar(form))
				.addObject("atuacoes", Atuacao.values())
				.addObject("farois", Farol.valores())
				.addObject("turmas", turmaService.listar())
				.addObject("squads", squadService.listar());
	}
	
	private StartForm model() {
		StartForm model = new StartForm();
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
	public ModelAndView salvar(@ModelAttribute("form") @Valid StartForm form, BindingResult result) {
		validator.validaSalvar(form, result);
		
		if(result.hasErrors()) {
			formFactory.setErro("Erro ao salvar Start");
			
			return form(form);
		}
		
		service.salvar(form);
		
		formFactory.setSucesso("Start salvo com sucesso");
		
		return new ModelAndView("redirect:/start");
	}

	@RequestMapping(value="listar", method= {RequestMethod.GET, RequestMethod.POST})
	@Transactional
	public ModelAndView listar(@ModelAttribute("form") ListaStartForm form, BindingResult result) {
		if(result.hasErrors()) {
			formFactory.setErro("Erro ao criar a lista");
			
			return formLista(form);
		}
		
		return formLista(form);
	}
	
	@GetMapping("billable")
	@Transactional
	public ModelAndView billable(@RequestParam(value="id") Long id, @RequestParam(value="viewAnterior") String viewAnterior) {
		String nome = service.billable(id);
		
		formFactory.setSucesso(nome + " tornado billable com sucesso");
				
		return new ModelAndView("redirect:/" + viewAnterior);
	}
	
	@GetMapping("relatorio")
	@Transactional
	public ModelAndView relatorio(@RequestParam(value="id") Long id) {
		StartRelatorioDto relatorio = service.relatorio(id);
		
		return formFactory.newModelAndView(FUNCIONALIDADE + "/relatorio")
				.addObject("relatorio", relatorio);
	}
}
