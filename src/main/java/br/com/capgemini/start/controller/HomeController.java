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
	
	public ModelAndView form(String sucesso, String erro) {
		return new ModelAndView("home")
				.addObject("agendamentos_todos", service.listarDoUsuarioLogadoTodos())
				.addObject("agendamentos_projeto", service.listarDoUsuarioLogadoProjeto())
				.addObject("sucesso", sucesso)
				.addObject("erro", erro)
				.addObject("usuario", Usuario.usuarioLogado());
	}
	
	@GetMapping("/")
	public ModelAndView index() {
		return form(null, null);
	}

	@GetMapping("home")
	public ModelAndView home() {
		return form(null, null);
	}
}
