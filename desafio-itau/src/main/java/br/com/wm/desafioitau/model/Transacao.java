package br.com.wm.desafioitau.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public class Transacao {
	
	@NotNull(message = "Valor não pode ser nulo.")
	@PositiveOrZero(message = "Valor não pode ser negativo.")
	private BigDecimal valor;
	
	@NotNull(message = "Data/hora não pode ser nula.")
	private OffsetDateTime dataHora;
	
	public Transacao(BigDecimal valor, OffsetDateTime dataHora) {
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
	
}
