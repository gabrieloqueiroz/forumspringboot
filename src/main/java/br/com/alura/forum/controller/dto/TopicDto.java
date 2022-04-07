package br.com.alura.forum.controller.dto;

import java.time.LocalDateTime;

import br.com.alura.forum.model.Topic;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class TopicDto {
	
	private Long id;
	private String message;
	private String title;
	private LocalDateTime creationDate;
	
	public TopicDto(Topic topic) {
		this.id = topic.getId();
		this.title = topic.getTitle();
		this.message = topic.getMessage();
		this.creationDate = topic.getCreationDate();
	}

	public static Page<TopicDto> converter(Page<Topic> topics) {
		return topics.map(TopicDto::new);
	}
}
