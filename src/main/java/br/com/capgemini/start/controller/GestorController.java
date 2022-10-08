package br.com.capgemini.start.controller;

import java.time.LocalDate;

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
import br.com.capgemini.start.model.CorProjeto;
import br.com.capgemini.start.model.Usuario;
import br.com.capgemini.start.model.form.GestorForm;
import br.com.capgemini.start.service.GestorService;
import br.com.capgemini.start.validation.UsuarioValidator;

@RestController
@RequestMapping("gestor")
public class GestorController {

	private static final String FUNCIONALIDADE = "gestor";
	
	@Autowired
	private GestorService service;
	
	@Autowired
	private UsuarioValidator usuarioValidator;

	private ModelAndView form(GestorForm form, String sucesso, String erro) {
		return new ModelAndView(FUNCIONALIDADE + "/form")
				.addObject("form", form)
				.addObject("cores", CorProjeto.valores())
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
	
	private GestorForm model() {
		GestorForm model = new GestorForm();
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
	public ModelAndView salvar(@ModelAttribute("form") @Valid GestorForm form, BindingResult result) {
		usuarioValidator.validaSalvar(form.getId(), form.getEmail(), result);
		
		if(result.hasErrors()) {
			return form(form, null, "Erro ao salvar Gestor");
		}
		
		service.salvar(form);
		
		return form(model(), "Gestor salvo com sucesso", null);
	}

	//@GetMapping("excluir")
	@Transactional
	public ModelAndView excluir(@RequestParam("id") Long id) {
		try {
			service.excluir(id);
			return formLista("Gestor excluído com sucesso", null);
		} catch (ViewException e) {
			return formLista(null, "Gestor não pode ser excluído, motivo(s): "+e.getErro());
		}
	}

	@GetMapping("listar")
	@Transactional
	public ModelAndView listar() {
		return formLista(null, null);
	}
}
