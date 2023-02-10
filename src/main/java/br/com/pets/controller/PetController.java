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
import br.com.pets.service.PetService;

@Controller
@RequestMapping()
public class PetController {
	
	@Autowired
	private PetService service;

	private ModelAndView form(Pet model) {
		return new ModelAndView("form")
				.addObject("model", model)
				.addObject("sexos", Sexo.values());		
	}
	
	private ModelAndView form(List<Pet> lista) {
		return new ModelAndView("table")
				.addObject("lista", lista);
	}
	
//	@GetMapping("form")
//	public ModelAndView pegar(@RequestParam(value="id", required = false) Integer id) {
//		return form(id != null ? service.pegar(id) : new Pet());
//	}
	
	@PostMapping("form")
	public ModelAndView salvar(@ModelAttribute("model") @Valid Pet model, BindingResult result) {
		if(result.hasErrors()) {
			return form(model);
		}
		
		service.salvar(model);
		
		return form(new Pet());
	}

	@GetMapping("excluir")
	public ModelAndView excluir(@RequestParam(value="id") Integer id) {
		service.excluir(id);
		return form(service.listar());
	}
	
	@GetMapping("listar")
	public ModelAndView listar() {
		return form(service.listar());
	}
	
	@GetMapping("form")
	public ModelAndView formView() {
		return form(new Pet());
	}
	
}
