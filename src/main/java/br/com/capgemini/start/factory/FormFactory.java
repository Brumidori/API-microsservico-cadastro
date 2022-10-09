package br.com.capgemini.start.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.capgemini.start.exception.ErroInternoException;
import lombok.Setter;

@Setter
@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class FormFactory {

	@Autowired
	private UsuarioLogadoFactory usuarioLogadoFactory;
	
	private String sucesso;
	private String erro;
	private String alerta;

	private String sucesso() {
		String sucesso = this.sucesso;
		this.sucesso = null;
		
		return sucesso;
	}
	
	private String erro() {
		String erro = this.erro;
		this.erro = null;
		
		return erro;
	}
	
	private String alerta() {
		String alerta = this.alerta;
		this.alerta = null;
		
		return alerta;
	}
	
	public ModelAndView newModelAndView(String viewName) {
		return new ModelAndView(viewName)
				.addObject("usuario", usuarioLogadoFactory.usuarioLogado().orElseThrow(()-> new ErroInternoException("Usuario logado não encontrado")))
				.addObject("sucesso", sucesso())
				.addObject("erro", erro())
				.addObject("alerta", alerta());
	}
}
