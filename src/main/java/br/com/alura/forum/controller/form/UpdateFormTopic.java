package br.com.alura.forum.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.alura.forum.model.Topic;
import br.com.alura.forum.repository.TopicRepository;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateFormTopic {

	@NotNull@NotEmpty
	private String message;
	
	@NotNull @NotEmpty
	private String title;

	public Topic update(Long id, TopicRepository topicRepository) {
		
		Topic topic = topicRepository.getOne(id);
		
		topic.setMessage(this.message);
		topic.setTitle(this.title);

		return topic;
	}
}
