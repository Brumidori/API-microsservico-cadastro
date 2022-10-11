package br.com.capgemini.start.factory;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.capgemini.start.model.Coach;
import br.com.capgemini.start.model.Gestor;
import br.com.capgemini.start.model.Start;
import br.com.capgemini.start.model.Usuario;
import br.com.capgemini.start.model.dto.TipoUsuarioDto;
import br.com.capgemini.start.model.dto.UsuarioDto;
import br.com.capgemini.start.repository.UsuarioRepository;

@Component
public class UsuarioLogadoFactory {
	
	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private ModelMapper mapper;
	
	public Optional<UsuarioDto> usuarioLogado() {
		try {
			Usuario usuarioSession = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			
			Optional<Usuario> optional = repository.findById(usuarioSession.getId());
			
			return optional.isPresent() ? Optional.of(newUsuarioDto(optional.get())) : Optional.empty();
		} catch (Exception e) {
			return Optional.empty();
		}
	}
	
	public UsuarioDto newUsuarioDto(Usuario usuario) {
		UsuarioDto dto = mapper.map(usuario, UsuarioDto.class);
		if (usuario instanceof Gestor) {
			dto.setTipo(TipoUsuarioDto.GESTOR);
		} else if (usuario instanceof Coach) {
			dto.setTipo(TipoUsuarioDto.COACH);
		} else if (usuario instanceof Start){
			dto.setTipo(TipoUsuarioDto.START);
		}
		
		return dto;
	}
}
