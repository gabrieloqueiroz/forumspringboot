package br.com.alura.forum.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controller.dto.TopicDetails;
import br.com.alura.forum.controller.dto.TopicDto;
import br.com.alura.forum.controller.form.AtualizaTopicoForm;
import br.com.alura.forum.controller.form.TopicosForm;
import br.com.alura.forum.model.Topic;
import br.com.alura.forum.repository.CourseRepository;
import br.com.alura.forum.repository.TopicRepository;

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
	public Page<TopicDto> listTopics(@RequestParam(required = false) String courseName, @RequestParam int page, @RequestParam int quantity) {

		Pageable pagination = PageRequest.of(page, quantity);

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
	public ResponseEntity<TopicDto> cadastrar(@RequestBody @Valid TopicosForm form, UriComponentsBuilder uriBuilder) {
		Topic topic = form.converter(courseRepository);
		topicRepository.save(topic);

		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topic.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicDto(topic));
		

	}

	@GetMapping("/{id}")
	public ResponseEntity<TopicDetails> detalhar (@PathVariable Long id) { //PathVariable diz que o id ira vir direto na URL. Não vai utilizar "?" vai vir direto após a barra
		
		Optional<Topic> topico = topicRepository.findById(id);
		
		
		if(topico.isPresent()) {
					
			return ResponseEntity.ok(new TopicDetails(topico.get()));
		}
		
		return ResponseEntity.notFound().build();
		
	
	}
	
	
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizaTopicoForm form ) {
			
		Topic topic = form.atualiza(id, topicRepository);
		
		
		return ResponseEntity.ok(new TopicDto(topic));

	}
	
	
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> deletar(@PathVariable Long id) {
		
		 topicRepository.deleteById(id);
		

		 return ResponseEntity.ok().build();
	}
		

}
