package br.com.capgemini.start.model.dto;

import java.io.Serializable;

import br.com.capgemini.start.model.Permissao;
import br.com.capgemini.start.model.Regra;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UsuarioDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String email;
	private String nome;
	private Permissao permissao;
	private TipoUsuarioDto tipo;
	
	public boolean hasAnyRole(String role) {
		for (Regra regra : permissao.getRegras()) {
			if(regra.getAuthority().equals(role)) {
				return true;
			}
		}
		return false;
	}
}
