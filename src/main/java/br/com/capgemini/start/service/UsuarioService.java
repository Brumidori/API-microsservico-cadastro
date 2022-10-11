package br.com.capgemini.start.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.capgemini.start.exception.ErroInternoException;
import br.com.capgemini.start.model.Usuario;
import br.com.capgemini.start.model.form.AlterarSenhaForm;
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

	public String senha(Long id, boolean reiniciarSenha) {
		if(id == null || reiniciarSenha) {
			return new BCryptPasswordEncoder().encode("1234");
		}

		return repository.findById(id).orElseThrow(()-> new ErroInternoException("Usuario não encontrado ao pegar a senha"))
				.getPassword();
	}

	public void alterarSenha(AlterarSenhaForm form) {
		try {
			Usuario usuarioSession = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			Usuario usuario = repository.findById(usuarioSession.getId()).orElseThrow(()-> new ErroInternoException("Usuario não encontrado ao alterar senha"));
			usuario.setPassword(new BCryptPasswordEncoder().encode(form.getSenha()));
			repository.save(usuario);
			
		} catch (ErroInternoException e) {
			throw e;
		} catch (Exception e) {
			throw new ErroInternoException("Erro ao alterar senha do usuário");
		}
	}
}
