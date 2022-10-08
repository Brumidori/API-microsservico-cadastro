package br.com.capgemini.start.conf;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.capgemini.start.exception.ErroInternoException;
import br.com.capgemini.start.model.Usuario;

@Controller
public class ErroController implements ErrorController {
	
	@RequestMapping("/error")
	public ModelAndView handleError(HttpServletRequest request) {
		Integer statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
		Exception e = (Exception) request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
		
		List<String> erros = new ArrayList<>();
		
		switch (statusCode) {
			case 400: erros.add("Erro de Requisição");
			break;
			case 403: erros.add("Acesso Negado!!!");
			break;
			case 404: erros.add("Mapeamento inválido");
			break;
			case 405: erros.add("Metodo não suportado");
			break;
			case 500: erros.add("Erro Interno do Sistema");
			break;
			default: erros.add("Erro Genérico");
		}
		
		if(e != null) {
			e.printStackTrace();
			
			if (e instanceof ErroInternoException) {
				ErroInternoException eie = (ErroInternoException) e;
				erros.addAll(eie.getErros());
			} else {
				erros.add(e.getMessage());
				
				if(e.getCause() != null) {
					erros.add(e.getCause().getMessage());
					
					if(e.getCause().getCause() != null) {
						erros.add(e.getCause().getCause().getMessage());
					}
				}
			}
		}
		
		return new ModelAndView("erro")
				.addObject("statusCode", statusCode)
				.addObject("erros", erros)
				.addObject("usuario", usuarioLogado());
	}

	private Usuario usuarioLogado() {
		try {
			return Usuario.usuarioLogado();
		} catch (Exception e) {
			return null;
		}
	}
}
