package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;

import br.com.alura.forum.model.Answer;

public class AnswerDto {

	private Long id;
	private String nomeAutor;
	private String mensagem;
	private LocalDateTime dataCriacao;

	public AnswerDto(Answer answer) {
		this.id = answer.getId();
		this.nomeAutor = answer.getAutor().getName();
		this.mensagem = answer.getMensagem();
		this.dataCriacao = answer.getDataCriacao();
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
