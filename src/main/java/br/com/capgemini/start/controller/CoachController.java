package br.com.capgemini.start.controller;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import br.com.capgemini.start.exception.ViewException;
import br.com.capgemini.start.model.Atuacao;
import br.com.capgemini.start.model.Usuario;
import br.com.capgemini.start.model.dto.CoachDto;
import br.com.capgemini.start.model.form.CoachForm;
import br.com.capgemini.start.service.CoachService;
import br.com.capgemini.start.service.GestorService;
import br.com.capgemini.start.validation.UsuarioValidator;

@RestController
@RequestMapping("coach")
public class CoachController {

	private static final String FUNCIONALIDADE = "coach";
	
	@Autowired
	private CoachService service;
	
	@Autowired
	private GestorService gestorService;
	
	@Autowired
	private UsuarioValidator usuarioValidator;

	private ModelAndView form(CoachForm form, String sucesso, String erro) {
		return new ModelAndView(FUNCIONALIDADE + "/form")
				.addObject("form", form)
				.addObject("atuacoes", Atuacao.values())
				.addObject("gestores", gestorService.listar())
				.addObject("sucesso", sucesso)
				.addObject("erro", erro)
				.addObject("usuario", Usuario.usuarioLogado());
	}
	
	private ModelAndView formLista(List<CoachDto> lista, String sucesso, String erro) {
		return new ModelAndView(FUNCIONALIDADE + "/lista")
				.addObject("lista", lista)
				.addObject("sucesso", sucesso)
				.addObject("erro", erro)
				.addObject("usuario", Usuario.usuarioLogado());
	}
	
	private CoachForm model() {
		CoachForm model = new CoachForm();
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
	public ModelAndView salvar(@ModelAttribute("form") @Valid CoachForm form, BindingResult result) {
		usuarioValidator.validaSalvar(form.getId(), form.getEmail(), result);
		
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
			return formLista(service.listar(), "Start excluído com sucesso", null);
		} catch (ViewException e) {
			return formLista(service.listar(), null, "Start não pode ser excluído, motivo(s): "+e.getErro());
		}
	}

	@GetMapping("listar")
	@Transactional
	public ModelAndView listar() {
		return formLista(service.listar(), null, null);
	}
	
	@GetMapping("rest/listar/gestor/{idGestor}")
	@Transactional
	@ResponseBody
	public List<CoachDto> restListar(@PathVariable("idGestor") Long idGestor) {
		return service.listarGestor(idGestor);
	}
}
