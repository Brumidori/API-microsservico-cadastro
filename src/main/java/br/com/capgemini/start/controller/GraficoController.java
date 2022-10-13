package br.com.capgemini.start.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.capgemini.start.service.GraficoService;

@RestController
@RequestMapping("grafico")
public class GraficoController {

	@Autowired
	private GraficoService service;
	
	@GetMapping(value="start/avaliacoes", produces=MediaType.IMAGE_PNG_VALUE)
	public byte[] startAvaliacoes(@RequestParam(value="idStart") Long idStart) {
		return service.startAvaliacoes(idStart);
	}
	
	@GetMapping(value="start/pizza/farois/turma", produces=MediaType.IMAGE_PNG_VALUE)
	public byte[] startPizzaFaroisTurma(@RequestParam(value="idTurma") Long idTurma) {
		return service.startPizzaFaroisTurma(idTurma);
	}
}
