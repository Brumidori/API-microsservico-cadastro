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
import br.com.capgemini.start.model.form.AvaliacaoForm;
import br.com.capgemini.start.model.form.AvaliacaoListaForm;
import br.com.capgemini.start.service.AvaliacaoService;

@RestController
@RequestMapping("avaliacao")
public class AvaliacaoController {

	private static final String FUNCIONALIDADE = "avaliacao";
	
	@Autowired
	private AvaliacaoService service;
	
	@Autowired
	private StartController startController;

	private ModelAndView form(AvaliacaoForm form, String sucesso, String erro) {
		return new ModelAndView(FUNCIONALIDADE + "/form")
				.addObject("form", form)
				.addObject("farois", Farol.valores())
				.addObject("sucesso", sucesso)
				.addObject("erro", erro)
				.addObject("usuario", Usuario.usuarioLogado());
	}
	
	@GetMapping
	@Transactional
	public ModelAndView pegar(@RequestParam(value="id", required = false) Long id) {
		return form(id != null ? service.form(id) : new AvaliacaoForm(), null, null);
	}
	
	@PostMapping
	@Transactional
	public ModelAndView salvar(@ModelAttribute("form") @Valid AvaliacaoForm form, BindingResult result) {
		if(result.hasErrors()) {
			return form(form, null, "Erro ao salvar Avaliação");
		}
		
		service.salvar(form);
		
		return form(new AvaliacaoForm(), "Avaliação salva com sucesso", null);
	}
	
	@PostMapping("lista")
	@Transactional
	public ModelAndView salvar(@Valid AvaliacaoListaForm form) {
		service.salvar(form);
		return startController.formLista(form.newListaStartForm(), "Avaliação salva com sucesso", null);
	}
}
