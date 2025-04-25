package br.com.wm.desafioitau.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.wm.desafioitau.dto.TransacaoDTO;
import br.com.wm.desafioitau.service.TransacaoService;

@WebMvcTest(TransacaoController.class)
class TransacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TransacaoService transacaoService; // mockado via config abaixo
    
    @TestConfiguration
    static class TestConfig {
        @Bean
        public TransacaoService transacaoService() {
            return Mockito.mock(TransacaoService.class);
        }
    }

    @Test
    void deveAdicionarTransacaoComSucesso() throws Exception {
        TransacaoDTO dto = new TransacaoDTO(new BigDecimal("100.00"), OffsetDateTime.now());

        Mockito.doNothing().when(transacaoService).adicionar(Mockito.any());

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    void deveLimparTransacoesComSucesso() throws Exception {
        Mockito.doNothing().when(transacaoService).limpar();

        mockMvc.perform(delete("/transacao"))
                .andExpect(status().isOk());
    }

    @Test
    void deveRetornar422ParaTransacaoInvalida() throws Exception {
        TransacaoDTO dto = new TransacaoDTO(null, null);

        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isUnprocessableEntity());
    }
    
    @Test
    void deveRetornar400ParaTransacaoSemCorpo() throws Exception {
        mockMvc.perform(post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}