package br.com.wm.desafioitau.dto;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import br.com.wm.desafioitau.model.Transacao;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class TransacaoDTO {

	@NotNull(message = "Valor não pode ser nulo.")
	@PositiveOrZero(message = "Valor não pode ser negativo.")
	private BigDecimal valor;
	
	@NotNull(message = "Data/hora não pode ser nula.")
	private OffsetDateTime dataHora;
	
	public TransacaoDTO(BigDecimal valor, OffsetDateTime dataHora) {
		super();
		this.valor = valor;
		this.dataHora = dataHora;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public OffsetDateTime getDataHora() {
		return dataHora;
	}
	
	public Transacao toModel() {
		return new Transacao(valor, dataHora);
	}
	
	public static TransacaoDTO fromModel(Transacao transacao) {
		return new TransacaoDTO(transacao.getValor(), transacao.getDataHora());
	}
}
