package br.com.capgemini.start.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.capgemini.start.model.Usuario;
import br.com.capgemini.start.service.AgendamentoService;

@Controller
public class HomeController {
	
	@Autowired
	private AgendamentoService service;
	
	private ModelAndView form() {
		return new ModelAndView("home")
				.addObject("agendamentos_todos", service.listarDoUsuarioLogadoTodos())
				.addObject("agendamentos_projeto", service.listarDoUsuarioLogadoProjeto())
				.addObject("usuario", Usuario.usuarioLogado());
	}
	
	@GetMapping("/")
	public ModelAndView index() {
		return form();
	}

	@GetMapping("home")
	public ModelAndView home() {
		return form();
	}
}
