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

import br.com.pets.exception.PetException;
import br.com.pets.model.Cor;
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

	private ModelAndView view(Especie especieModel, String sucesso, String erro) {
		return new ModelAndView("especie/formEspecie").addObject("cores", Cor.values())
				.addObject("especieModel", especieModel).addObject("sucesso", sucesso).addObject("erro", erro);
	}

	private ModelAndView view(List<Especie> especieLista, String sucesso, String erro) {
		return new ModelAndView("especie/tableEspecie").addObject("lista", especieLista).addObject("sucesso", sucesso)
				.addObject("erro", erro);
	}

	@PostMapping("form")
	public ModelAndView salvar(@ModelAttribute("especieModel") @Valid Especie especieModel, BindingResult result) {
		validator.salvar(especieModel, result);
		if (result.hasErrors()) {
			return view(especieModel, null, "Erro ao salvar especie");
		}
		service.salvar(especieModel);
		return view(new Especie(), "Especie salva com sucesso", null);
	}

	@GetMapping("excluir")
	public ModelAndView excluir(@RequestParam(value = "id") Integer id) {
		try {
			validator.excluir(id);
			service.excluir(id);
			return view(service.listar(), "Espécie excluida com sucesso", null);
		} catch (PetException e) {
			return view(service.listar(), null, e.getErro());
		}
	}

	@GetMapping("form")
	public ModelAndView pegar(@RequestParam(value = "id", required = false) Integer id) {
		return view(id != null ? service.pegar(id) : new Especie(), null, null);
	}

	@GetMapping("listar")
	public ModelAndView listar() {
		return view(service.listar(), null, null);
	}

}
