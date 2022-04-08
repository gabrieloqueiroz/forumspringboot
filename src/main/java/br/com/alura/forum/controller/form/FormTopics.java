package br.com.alura.forum.controller.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import br.com.alura.forum.model.Course;
import br.com.alura.forum.model.Topic;
import br.com.alura.forum.repository.CourseRepository;

@Getter
@Setter
public class FormTopics {

	@NotNull
	@NotEmpty
	@Length(min = 5)
	private String title;

	@NotNull
	@NotEmpty
	@Length(min = 5)
	private String message;

	@NotNull
	@NotEmpty
	private String courseName;

	public Topic convert(CourseRepository courseRepository) {
		Course course = courseRepository.findByName(courseName);
		return new Topic(title, message, course);
	}

}
