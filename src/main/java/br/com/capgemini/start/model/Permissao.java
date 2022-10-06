package br.com.capgemini.start.model;

import java.util.Arrays;
import java.util.List;

import lombok.Getter;

@Getter
public enum Permissao {
	ADM("Adm", true, Regra.EDITAR, Regra.LER, Regra.BILLABLE), 
	GESTOR("Gestor", false, Regra.LER, Regra.BILLABLE_PROJETO),
	COACH("Coach", false, Regra.LER),
	START("Start", false);
	
	private final String imprime;
	private boolean adm;
	private final List<Regra> regras;
	
	private Permissao(String imprime, boolean adm, Regra ... regra) {
		this.imprime = imprime;
		this.adm = adm;
		this.regras = Arrays.asList(regra);
	}
}
