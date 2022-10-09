package br.com.capgemini.start.factory;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.capgemini.start.model.Usuario;
import br.com.capgemini.start.repository.UsuarioRepository;

@Component
public class UsuarioLogadoFactory {
	
	@Autowired
	private UsuarioRepository repository;
	
	public Optional<Usuario> usuarioLogado() {
		try {
			Usuario usuarioSession = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			return repository.findById(usuarioSession.getId());
		} catch (Exception e) {
			return Optional.empty();
		}
	}
}
