package br.unit.web.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Banco {
	@Id
	private String conta;
	private int valor;
	private int valorAnterior;

	public Banco() {
	}

	public Banco(String conta, int valor, int valorAnterior) {

		this.conta = conta;
		this.valor = valor;
		this.valorAnterior = valorAnterior;

	}

	public int getValorAnterior() {
		return valorAnterior;
	}

	public void setValorAnterior(int valorAnterior) {
		this.valorAnterior = valorAnterior;
	}

	public String getConta() {
		return conta;
	}

	public void setConta(String conta) {
		this.conta = conta;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

}
