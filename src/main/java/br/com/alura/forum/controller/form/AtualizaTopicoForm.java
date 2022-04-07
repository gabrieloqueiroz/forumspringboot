package br.com.alura.forum.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.alura.forum.model.Topic;
import br.com.alura.forum.repository.TopicRepository;

public class AtualizaTopicoForm {

	@NotNull@NotEmpty
	private String mensagem;
	
	@NotNull @NotEmpty
	private String titulo;

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Topic atualiza(Long id, TopicRepository topicoRepository) {
		
		Topic topic = topicoRepository.getOne(id);
		
		topic.setMessage(this.mensagem);
		topic.setTitle(this.titulo);
		
		
		return topic;
	}

}
