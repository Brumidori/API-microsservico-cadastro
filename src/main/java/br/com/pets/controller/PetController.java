package br.com.pets.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.pets.model.Pet;
import br.com.pets.model.Sexo;
import br.com.pets.service.EspecieService;
import br.com.pets.service.PetService;

@Controller
@RequestMapping("pet")
public class PetController {

	@Autowired
	private PetService service;
	@Autowired
	private EspecieService especieService;

	private ModelAndView form(Pet model, String sucesso, String erro) {
		return new ModelAndView("pet/form").addObject("model", model)
				.addObject("especies", especieService.listar())
				.addObject("sexos", Sexo.values())
				.addObject("sucesso", sucesso)
				.addObject("erro", erro);
	}

	private ModelAndView form(List<Pet> lista, String sucesso, String erro) {
		return new ModelAndView("pet/table")
				.addObject("lista", lista)
				.addObject("sucesso", sucesso)
				.addObject("erro", erro);
	}

	@GetMapping("form")
	public ModelAndView pegar(@RequestParam(value = "id", required = false) Integer id) {
		return form(id != null ? service.pegar(id) : new Pet(), null, null);
	}

	@PostMapping("form")
	public ModelAndView salvar(@ModelAttribute("model") @Valid Pet model, BindingResult result) {
		if (result.hasErrors()) {
			return form(model, null, "Erro ao salvar Pet"); // devolve os dados preenchidos
		}

		service.salvar(model);

		return form(new Pet(), "Pet salvo com sucesso", null);
	}

	@GetMapping("excluir")
	public ModelAndView excluir(@RequestParam(value = "id") Integer id) {
		service.excluir(id);
		return form(service.listar(), "Pet excluido com sucesso", null);
	}

	@GetMapping("listar")
	public ModelAndView listar() {
		return form(service.listar(), null, null);
	}

}
