package br.com.wm.desafioitau.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.wm.desafioitau.exception.ValidacaoException;
import br.com.wm.desafioitau.model.Transacao;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@SpringBootTest
public class TransacaoServiceTest {
	
	private TransacaoService transacaoService;
    private Validator validator;

    @BeforeEach
    void setUp() {
        transacaoService = new TransacaoService();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveAdicionarTransacaoValida() {
        Transacao transacao = new Transacao(new BigDecimal("100.00"), OffsetDateTime.now().minusMinutes(1));

        // validação manual
        Set<ConstraintViolation<Transacao>> violacoes = validator.validate(transacao);
        assertTrue(violacoes.isEmpty());

        transacaoService.adicionar(transacao);
        List<Transacao> todas = transacaoService.listarTodas();

        assertEquals(1, todas.size());
        assertEquals(transacao, todas.get(0));
    }

    @Test
    void deveLancarExcecaoParaTransacaoFutura() {
        Transacao transacaoFutura = new Transacao(new BigDecimal("50.00"), OffsetDateTime.now().plusMinutes(5));

        assertThrows(ValidacaoException.class, () -> {
            transacaoService.adicionar(transacaoFutura);
        });
    }

    @Test
    void deveRetornarListaImutavelDasTransacoes() {
        Transacao transacao = new Transacao(new BigDecimal("10.00"), OffsetDateTime.now().minusSeconds(10));
        transacaoService.adicionar(transacao);

        List<Transacao> todas = transacaoService.listarTodas();
        assertThrows(UnsupportedOperationException.class, () -> todas.add(transacao));
    }

    @Test
    void deveLimparTodasAsTransacoes() {
        transacaoService.adicionar(new Transacao(new BigDecimal("20.00"), OffsetDateTime.now().minusMinutes(2)));
        transacaoService.adicionar(new Transacao(new BigDecimal("30.00"), OffsetDateTime.now().minusMinutes(10)));

        transacaoService.limpar();
        List<Transacao> todas = transacaoService.listarTodas();

        assertTrue(todas.isEmpty());
    }

    @Test
    void deveDetectarViolacoesDeValidacaoComValorNuloENegativo() {
        Transacao invalida1 = new Transacao(null, OffsetDateTime.now());
        Transacao invalida2 = new Transacao(new BigDecimal("-5.00"), OffsetDateTime.now());
        
        Set<ConstraintViolation<Transacao>> violacoes1 = validator.validate(invalida1);
        Set<ConstraintViolation<Transacao>> violacoes2 = validator.validate(invalida2);

        assertFalse(violacoes1.isEmpty());
        assertTrue(violacoes1.stream().anyMatch(v -> v.getMessage().equals("Valor não pode ser nulo.")));

        assertFalse(violacoes2.isEmpty());
        assertTrue(violacoes2.stream().anyMatch(v -> v.getMessage().equals("Valor não pode ser negativo.")));
    }

    @Test
    void deveDetectarViolacaoDeValidacaoParaDataHoraNula() {
        Transacao invalida = new Transacao(new BigDecimal("10.00"), null);

        Set<ConstraintViolation<Transacao>> violacoes = validator.validate(invalida);

        assertFalse(violacoes.isEmpty());
        assertTrue(violacoes.stream().anyMatch(v -> v.getMessage().equals("Data/hora não pode ser nula.")));
    }

}
