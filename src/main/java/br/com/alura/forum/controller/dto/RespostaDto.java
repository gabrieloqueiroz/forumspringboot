package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;

import br.com.alura.forum.modelo.Resposta;

public class RespostaDto {

	private Long id;
	private String nomeAutor;
	private String mensagem;
	private LocalDateTime dataCriacao;

	public RespostaDto(Resposta resposta) {
		this.id = resposta.getId();
		this.nomeAutor = resposta.getAutor().getNome();
		this.mensagem = resposta.getMensagem();
		this.dataCriacao = resposta.getDataCriacao();
	}

	public Long getId() {
		return id;
	}

	public String getNomeAutor() {
		return nomeAutor;
	}

	public String getMensagem() {
		return mensagem;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

}
