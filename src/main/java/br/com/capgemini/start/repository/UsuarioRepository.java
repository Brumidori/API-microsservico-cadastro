package br.com.capgemini.start.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.capgemini.start.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	List<Usuario> findByUsername(String username);
	
	boolean existsByUsername(String username);

	boolean existsByIdNotAndUsername(Long id, String username);
	
	public default boolean existsByEmail(String email) {
		return existsByUsername(email);
	}

	public default boolean existsByIdNotAndEmail(Long id, String email){
		return existsByIdNotAndUsername(id, email);
	}
}
