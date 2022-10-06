package br.com.capgemini.start.controller;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value={"/login", "login"})
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class LoginController {
	
	@GetMapping
	public ModelAndView login(@RequestParam(value="error", required=false) String error) {
		return new ModelAndView("login/form")
				.addObject("erro", error);
	}
}
