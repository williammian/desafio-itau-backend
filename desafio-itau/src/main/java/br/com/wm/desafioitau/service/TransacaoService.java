package br.com.wm.desafioitau.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;

import br.com.wm.desafioitau.exception.ValidacaoException;
import br.com.wm.desafioitau.model.Transacao;
import jakarta.validation.Valid;

@Service
public class TransacaoService {
	private final List<Transacao> transacoes = new CopyOnWriteArrayList<>();
	
	public void adicionar(@Valid Transacao transacao) {
		if(transacao.getDataHora().isAfter(OffsetDateTime.now())) {
			throw new ValidacaoException("Transação não deve acontecer no futuro.");
		}
        transacoes.add(transacao);
    }

    public List<Transacao> listarTodas() {
        return List.copyOf(transacoes);
    }

    public void limpar() {
        transacoes.clear();
    }
}
