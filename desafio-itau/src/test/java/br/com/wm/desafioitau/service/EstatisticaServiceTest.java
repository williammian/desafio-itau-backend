package br.com.wm.desafioitau.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.wm.desafioitau.dto.EstatisticaDTO;
import br.com.wm.desafioitau.model.Transacao;

@SpringBootTest
public class EstatisticaServiceTest {

	private EstatisticaService estatisticaService;
    private TransacaoService transacaoServiceMock;

    @BeforeEach
    void setUp() {
        transacaoServiceMock = Mockito.mock(TransacaoService.class);
        estatisticaService = new EstatisticaService();
        estatisticaService.transacaoService = transacaoServiceMock;
    }

    @Test
    void deveRetornarEstatisticaZeradaQuandoNaoHaTransacoesRecentes() {
        when(transacaoServiceMock.listarTodas()).thenReturn(Collections.emptyList());

        EstatisticaDTO estatistica = estatisticaService.obterEstatistica();

        assertEquals(0, estatistica.getCount());
        assertEquals(0, estatistica.getSum());
        assertEquals(0, estatistica.getAvg());
        assertEquals(0, estatistica.getMax());
        assertEquals(0, estatistica.getMin());
    }

    @Test
    void deveRetornarEstatisticaApenasComTransacoesNosUltimos60Segundos() {
        OffsetDateTime agora = OffsetDateTime.now();

        Transacao t1 = new Transacao(new BigDecimal("10.00"), agora.minusSeconds(30));
        Transacao t2 = new Transacao(new BigDecimal("20.00"), agora.minusSeconds(10));
        Transacao t3 = new Transacao(new BigDecimal("5.00"), agora.minusMinutes(2)); // deve ser ignorada

        when(transacaoServiceMock.listarTodas()).thenReturn(List.of(t1, t2, t3));

        EstatisticaDTO estatistica = estatisticaService.obterEstatistica();

        assertEquals(2, estatistica.getCount());
        assertEquals(30.0, estatistica.getSum());
        assertEquals(15.0, estatistica.getAvg());
        assertEquals(20.0, estatistica.getMax());
        assertEquals(10.0, estatistica.getMin());
    }

    @Test
    void deveIgnorarTransacoesComMaisDe60Segundos() {
        OffsetDateTime agora = OffsetDateTime.now();
        Transacao antiga = new Transacao(new BigDecimal("100.00"), agora.minusSeconds(70));

        when(transacaoServiceMock.listarTodas()).thenReturn(List.of(antiga));

        EstatisticaDTO estatistica = estatisticaService.obterEstatistica();

        assertEquals(0, estatistica.getCount());
        assertEquals(0, estatistica.getSum());
    }
	
}
