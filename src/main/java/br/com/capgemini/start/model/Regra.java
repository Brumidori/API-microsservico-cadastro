package br.com.capgemini.start.model;

import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Regra implements GrantedAuthority {
	EDITAR("ROLE_EDITAR"), 
	LER("ROLE_LER"),
	BILLABLE("ROLE_BILLABLE"),
	BILLABLE_PROJETO("ROLE_BILLABLE_PROJETO");
	
	private final String authority;
}
