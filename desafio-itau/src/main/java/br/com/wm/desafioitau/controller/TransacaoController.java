package br.com.wm.desafioitau.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.wm.desafioitau.dto.TransacaoDTO;
import br.com.wm.desafioitau.service.TransacaoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {
	
	@Autowired
    private TransacaoService transacaoService;
	
	@PostMapping
    public ResponseEntity<Void> adicionarTransacao(@RequestBody @Valid TransacaoDTO transacaoDTO) {
        transacaoService.adicionar(transacaoDTO.toModel());
        return ResponseEntity.created(null).build();
    }
	
	@DeleteMapping
    public ResponseEntity<Void> limparTransacoes() {
        transacaoService.limpar();
        return ResponseEntity.ok().build();
    }
}
