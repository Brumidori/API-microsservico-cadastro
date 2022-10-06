package br.com.capgemini.start.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.capgemini.start.model.Usuario;
import br.com.capgemini.start.repository.UsuarioRepository;

@Service
public class UsuarioService implements UserDetailsService{

	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public Usuario loadUserByUsername(String email) throws UsernameNotFoundException {
		List<Usuario> usuarios = repository.findByUsername(email);
		if(usuarios.isEmpty()) {
			throw new UsernameNotFoundException("Usuario com e-mail:" + email + " não encontrado");
		}
		
		return usuarios.get(0);
	}
}
