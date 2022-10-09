package br.com.capgemini.start.model;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Usuario implements UserDetails, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "usuario_key_generator")
	private Long id;
	
	@Column(unique=true, nullable= false, name="email", length = 200)
	private String username;
	
	@Column(nullable= false, length = 200)
	private String nome;
	
	@Column(nullable= false, name="senha", length = 200)
	private String password;
	
	@Column(nullable= false, length = 20)
	@Enumerated(EnumType.STRING)
	private Permissao permissao;
	
	public boolean hasAnyRole(String role) {
		for (Regra regra : permissao.getRegras()) {
			if(regra.getAuthority().equals(role)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return permissao.getRegras();
	}
	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	

	public static void main(String[] args) {
		System.out.println(new BCryptPasswordEncoder().encode("1234"));
	}
}
