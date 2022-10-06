package br.com.capgemini.start.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
import br.com.capgemini.start.model.Atuacao;
import br.com.capgemini.start.model.Usuario;
import br.com.capgemini.start.model.dto.StartDto;
import br.com.capgemini.start.model.form.ListaStartForm;
import br.com.capgemini.start.model.form.StartForm;
import br.com.capgemini.start.service.CoachService;
import br.com.capgemini.start.service.GestorService;
import br.com.capgemini.start.service.StartService;
import br.com.capgemini.start.service.TurmaService;
import br.com.capgemini.start.validation.StartValidator;

@RestController
@RequestMapping("start")
public class StartController {

	private static final String FUNCIONALIDADE = "start";
	
	@Autowired
	private StartService service;
	
	@Autowired
	private TurmaService turmaService;
	
	@Autowired
	private CoachService coachService;
	
	@Autowired
	private GestorService gestorService;
	
	@Autowired
	private StartValidator validator;

	private ModelAndView form(StartForm form, String sucesso, String erro) {
		return new ModelAndView(FUNCIONALIDADE + "/form")
				.addObject("form", form)
				.addObject("atuacoes", Atuacao.values())
				.addObject("turmas", turmaService.listar())
				.addObject("gestores", gestorService.listar())
				.addObject("coachs", form.getIdGestor() != null ? coachService.listarGestor(form.getIdGestor()) : new ArrayList<>())
				.addObject("sucesso", sucesso)
				.addObject("erro", erro)
				.addObject("usuario", Usuario.usuarioLogado());
	}
	
	private ModelAndView formLista(ListaStartForm form, List<StartDto> lista, String sucesso, String erro) {
		return new ModelAndView(FUNCIONALIDADE + "/lista")
				.addObject("form", form)
				.addObject("lista", lista)
				.addObject("atuacoes", Atuacao.values())
				.addObject("sucesso", sucesso)
				.addObject("erro", erro)
				.addObject("usuario", Usuario.usuarioLogado());
	}
	
	private StartForm model() {
		StartForm model = new StartForm();
		model.setDataCadastro(LocalDate.now());
		return model;
	}
	
	@GetMapping
	@Transactional
	public ModelAndView pegar(@RequestParam(value="id", required = false) Long id) {
		return form(id != null ? service.form(id) : model(), null, null);
	}

	@PostMapping
	@Transactional
	public ModelAndView salvar(@ModelAttribute("form") @Valid StartForm form, BindingResult result) {
		validator.validaSalvar(form, result);
		
		if(result.hasErrors()) {
			return form(form, null, "Erro ao salvar Start");
		}
		
		service.salvar(form);
		
		return form(model(), "Start salvo com sucesso", null);
	}

	//@GetMapping("excluir")
	@Transactional
	public ModelAndView excluir(@RequestParam("id") Long id) {
		try {
			service.excluir(id);
			return formLista(new ListaStartForm(), service.listar(), "Start excluído com sucesso", null);
		} catch (ViewException e) {
			return formLista(new ListaStartForm(), service.listar(), null, "Start não pode ser excluído, motivo(s): "+e.getErro());
		}
	}
	
	@GetMapping("billable")
	@Transactional
	public ModelAndView billable(@RequestParam(value="id") Long id) {
		String nome = service.billable(id);
		
		return formLista(new ListaStartForm(), service.listar(), nome + " tornado billable com sucesso", null);
	}

	@GetMapping("listar")
	@Transactional
	public ModelAndView listar(@ModelAttribute("form") ListaStartForm form, BindingResult result) {
		if(result.hasErrors()) {
			return formLista(form, service.listar(form), null, "Erro ao criar a lista");
		}
		return formLista(form, service.listar(form), null, null);
	}
}
