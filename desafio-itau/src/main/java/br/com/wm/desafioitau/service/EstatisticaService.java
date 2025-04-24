package br.com.wm.desafioitau.service;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.wm.desafioitau.dto.EstatisticaDTO;
import br.com.wm.desafioitau.model.Transacao;

@Service
public class EstatisticaService {
	
	@Autowired
	TransacaoService transacaoService;
	
	public EstatisticaDTO obterEstatistica() {		
		OffsetDateTime agora = OffsetDateTime.now();
        OffsetDateTime limite = agora.minusSeconds(60);
        
        List<Transacao> transacoes = transacaoService.listarTodas().stream()
        		.filter(t -> t.getDataHora().isAfter(limite))
        		.collect(Collectors.toList());
        
        EstatisticaDTO estatisticaDTO = null;
                
        if(transacoes.isEmpty()) {
        	estatisticaDTO = new EstatisticaDTO(0, 0, 0, 0, 0);
        }else {
        	DoubleSummaryStatistics stats = transacoes.stream().mapToDouble(t -> t.getValor().doubleValue()).summaryStatistics();
        	estatisticaDTO = EstatisticaDTO.fromDoubleSumaryStatistics(stats);
        }
        
        return estatisticaDTO;
	}
	
}
