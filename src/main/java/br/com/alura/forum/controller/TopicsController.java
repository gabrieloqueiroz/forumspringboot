package br.com.alura.forum.controller;

import java.net.URI;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.TopicDetails;
import br.com.alura.forum.controller.dto.TopicDto;
import br.com.alura.forum.controller.form.UpdateFormTopic;
import br.com.alura.forum.controller.form.FormTopics;
import br.com.alura.forum.model.Topic;
import br.com.alura.forum.repository.CourseRepository;
import br.com.alura.forum.repository.TopicRepository;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/topics")
public class TopicsController {

	private TopicRepository topicRepository;
	private CourseRepository courseRepository;

	@Autowired
	public TopicsController(TopicRepository topicRepository, CourseRepository courseRepository) {
		this.topicRepository = topicRepository;
		this.courseRepository = courseRepository;
	}

	@GetMapping
	@Cacheable(value = "topicList")
	public Page<TopicDto> listTopics(
			@RequestParam(required = false) String courseName,
			@PageableDefault(sort = "id", direction = DESC) Pageable pagination
	) {

		if (courseName == null) {
			Page<Topic> topics = topicRepository.findAll(pagination);
			return TopicDto.converter(topics);
		} else {
			// Para navegar e trazer o filtro colocamos o nome do atributo. Como é um
			// relacionamento colocamos o Underline para ele navegar pelas classes
			Page<Topic> topics = topicRepository.findByCourse_Name(courseName, pagination);
			return TopicDto.converter(topics);
		}

	}

	/*
	 * @PostMapping public void cadastrar(@RequestBody TopicosForm form) { Topico
	 * topico = form.converter(cursoRepository);
	 * 
	 * topicoRepository.save(topico); }
	 */

	@PostMapping
	@Transactional
	@CacheEvict(value = "topicList", allEntries = true )
	public ResponseEntity<TopicDto> register(@RequestBody @Valid FormTopics form, UriComponentsBuilder uriBuilder) {
		Topic topic = form.convert(courseRepository);
		topicRepository.save(topic);

		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topic.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicDto(topic));
		

	}

	@GetMapping("/{id}")
	public ResponseEntity<TopicDetails> detailTopic(@PathVariable Long id) { //PathVariable diz que o id ira vir direto na URL. Não vai utilizar "?" vai vir direto após a barra
		
		Optional<Topic> topico = topicRepository.findById(id);

		if(topico.isPresent()) {
			return ResponseEntity.ok(new TopicDetails(topico.get()));
		}

		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "topicList", allEntries = true )
	public ResponseEntity<TopicDto> updateTopic(@PathVariable Long id, @RequestBody @Valid UpdateFormTopic form ) {
			
		Topic topic = form.update(id, topicRepository);
		
		return ResponseEntity.ok(new TopicDto(topic));
	}
	
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "topicList", allEntries = true )
	public ResponseEntity<?> deleteTopic(@PathVariable Long id) {
		
		 topicRepository.deleteById(id);

		 return ResponseEntity.ok().build();
	}
}
