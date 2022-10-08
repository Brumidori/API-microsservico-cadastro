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
import br.com.capgemini.start.model.form.TurmaForm;
import br.com.capgemini.start.service.TurmaService;

@RestController
@RequestMapping("turma")
public class TurmaController {

	private static final String FUNCIONALIDADE = "turma";
	
	@Autowired
	private TurmaService service;

	private ModelAndView form(TurmaForm form, String sucesso, String erro) {
		return new ModelAndView(FUNCIONALIDADE + "/form")
				.addObject("form", form)
				.addObject("sucesso", sucesso)
				.addObject("erro", erro)
				.addObject("usuario", Usuario.usuarioLogado());
	}
	
	private ModelAndView formLista(String sucesso, String erro) {
		return new ModelAndView(FUNCIONALIDADE + "/lista")
				.addObject("lista", service.listar())
				.addObject("sucesso", sucesso)
				.addObject("erro", erro)
				.addObject("usuario", Usuario.usuarioLogado());
	}
	
	@GetMapping
	@Transactional
	public ModelAndView pegar(@RequestParam(value="id", required = false) Long id) {
		return form(id != null ? service.form(id) : new TurmaForm(), null, null);
	}
	
	@PostMapping
	@Transactional
	public ModelAndView salvar(@ModelAttribute("form") @Valid TurmaForm form, BindingResult result) {
		if(result.hasErrors()) {
			return form(form, null, "Erro ao salvar Turma");
		}
		
		service.salvar(form);
		
		return form(new TurmaForm(), "Turma salva com sucesso", null);
	}

	//@GetMapping("excluir")
	@Transactional
	public ModelAndView excluir(@RequestParam("id") Long id) {
		try {
			service.excluir(id);
			return formLista("Turma excluída com sucesso", null);
		} catch (ViewException e) {
			return formLista(null, "Turma não pode ser excluído, motivo(s): "+e.getErro());
		}
	}

	@GetMapping("listar")
	@Transactional
	public ModelAndView listar() {
		return formLista(null, null);
	}
}
