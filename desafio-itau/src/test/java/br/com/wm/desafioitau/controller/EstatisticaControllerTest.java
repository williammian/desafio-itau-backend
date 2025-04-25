package br.com.wm.desafioitau.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.wm.desafioitau.dto.EstatisticaDTO;
import br.com.wm.desafioitau.service.EstatisticaService;
import br.com.wm.desafioitau.service.TransacaoService;

@WebMvcTest(EstatisticaController.class)
class EstatisticaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EstatisticaService estatisticaService;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public EstatisticaService estatisticaService() {
            return Mockito.mock(EstatisticaService.class);
        }
        @Bean
        public TransacaoService transacaoService() {
            return Mockito.mock(TransacaoService.class);
        }
    }

    @Test
    void deveRetornarEstatisticaComDadosValidos() throws Exception {
        EstatisticaDTO mockDTO = new EstatisticaDTO(2, 100.0, 50.0, 80.0, 20.0);

        Mockito.when(estatisticaService.obterEstatistica()).thenReturn(mockDTO);

        mockMvc.perform(get("/estatistica"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.sum").value(100.0))
                .andExpect(jsonPath("$.avg").value(50.0))
                .andExpect(jsonPath("$.max").value(80.0))
                .andExpect(jsonPath("$.min").value(20.0));
    }

    @Test
    void deveRetornar404ParaCaminhoInvalido() throws Exception {
        mockMvc.perform(get("/estatisticas")) // endpoint inválido
                .andExpect(status().isNotFound());
    }

    
}