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

import br.com.pets.model.Especie;
import br.com.pets.service.EspecieService;
import br.com.pets.validator.EspecieValidator;

@Controller
@RequestMapping("especie")
public class EspecieController {

	@Autowired
	private EspecieService service;
	
	@Autowired
	private EspecieValidator validator;
	
	private ModelAndView view(Especie especieModel) {
		return new ModelAndView("pet/formEspecie")
				.addObject("especieModel", especieModel);
	}

	private ModelAndView view(List<Especie> especieLista) {
		return new ModelAndView("pet/tableEspecie").addObject("lista", especieLista);
	}

	@PostMapping()
	public ModelAndView salvar(@ModelAttribute("especieModel") @Valid Especie especieModel, BindingResult result) {
		if (result.hasErrors()) {
			return view(especieModel); // devolve os dados preenchidos
		}
		
		if(!validator.validar(especieModel)) {
			service.salvar(especieModel);

		};


		return view(new Especie());
	}

	@GetMapping("excluir")
	public ModelAndView excluir(@RequestParam(value = "id") Integer id) {
		service.excluir(id);
		return view(service.listar());
	}

	@GetMapping("form")
	public ModelAndView formView() {
		return view(new Especie());
	}

	@GetMapping("listar")
	public ModelAndView listar() {
		return view(service.listar());
	}

}
