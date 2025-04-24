package br.com.wm.desafioitau.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wm.desafioitau.dto.EstatisticaDTO;
import br.com.wm.desafioitau.service.EstatisticaService;

@RestController
@RequestMapping("/estatistica")
public class EstatisticaController {

	@Autowired
    private EstatisticaService estatisticaService;
	
	@GetMapping()
    public ResponseEntity<EstatisticaDTO> obterEstatistica() {
		EstatisticaDTO estatisticaDTO = estatisticaService.obterEstatistica();
		return ResponseEntity.ok(estatisticaDTO);
    }
	
}
