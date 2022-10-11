package br.com.capgemini.start.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.capgemini.start.factory.FormFactory;
import br.com.capgemini.start.model.Farol;
import br.com.capgemini.start.service.HomeService;

@Controller
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class HomeController {

	private static final String FUNCIONALIDADE = "home";
	
	@Autowired
	private HomeService homeService;
	
	@Autowired
	private FormFactory formFactory;
	
	public ModelAndView form() {
		return formFactory.newModelAndView(FUNCIONALIDADE + "/home")
				.addObject("home", homeService.home())
				.addObject("farois", Farol.valores());
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
