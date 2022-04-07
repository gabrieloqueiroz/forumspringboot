package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.alura.forum.model.TopicStatus;
import br.com.alura.forum.model.Topic;

public class TopicDetails {

	private Long id;
	private String mensagem;
	private String titulo;
	private LocalDateTime dataCriacao;
	private String nomeAutor;
	private TopicStatus status;
	private List<AnswerDto> respostas;
	
	
	public TopicDetails(Topic topic) {
		this.id = topic.getId();
		this.titulo = topic.getTitle();
		this.mensagem = topic.getMessage();
		this.dataCriacao = topic.getCreationDate();
		this.nomeAutor = topic.getAuthor().getName();
		this.status = topic.getStatus();
		this.respostas = new ArrayList<>(); 
		this.respostas.addAll(topic.getRespostas().stream().map(AnswerDto::new).collect(Collectors.toList()));
		
	}

	public Long getId() {
		return id;
	}

	public String getMensagem() {
		return mensagem;
	}

	public String getTitulo() {
		return titulo;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public String getNomeAutor() {
		return nomeAutor;
	}

	public TopicStatus getStatus() {
		return status;
	}

	public List<AnswerDto> getRespostas() {
		return respostas;
	}

}
