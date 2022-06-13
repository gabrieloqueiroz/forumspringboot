package br.com.alura.forum.controller;

import br.com.alura.forum.controller.dto.TopicDetails;
import br.com.alura.forum.controller.dto.TopicDto;
import br.com.alura.forum.controller.form.FormTopics;
import br.com.alura.forum.controller.form.UpdateFormTopic;
import br.com.alura.forum.model.Topic;
import br.com.alura.forum.repository.CourseRepository;
import br.com.alura.forum.repository.TopicRepository;
import br.com.alura.forum.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.data.domain.Sort.Direction.DESC;

@RestController
@RequestMapping("/topics")
public class TopicsController {

	private TopicRepository topicRepository;
	private CourseRepository courseRepository;
	private TopicService topicService;

	@Autowired
	public TopicsController(TopicRepository topicRepository, CourseRepository courseRepository, TopicService topicService) {
		this.topicRepository = topicRepository;
		this.courseRepository = courseRepository;
		this.topicService = topicService;
	}

	@GetMapping
	@Cacheable(value = "topicList")	// Cacheable - primeira vez executado ele vai pegar linha por linha, das proximas ele vai utilizar ja oque esta em cache - Na aplicação podemos ter varios Cacheable, por parametro então informamos o identificar desse cache	
	public Page<TopicDto> listTopics(
			@RequestParam(required = false) String courseName, // Esse parametro diferente do PathVariable, ele é passado após o ponto de interrogação
			@PageableDefault(sort = "id", direction = DESC) Pageable pagination
	) {
		return topicService.findTopic(pagination, courseName);
	}

	@PostMapping
	@Transactional
	@CacheEvict(value = "topicList", allEntries = true ) // CacheEvict é utilizado em metodos ou endpoints que atualizam registros, então anotamos ele pra limpar os caches ja salvos, passamos como value o nome do cache allentries para invalidar todos os registros
	public ResponseEntity<TopicDto> register(@RequestBody @Valid FormTopics form, UriComponentsBuilder uriBuilder) {

		Topic topic = topicService.register(form);

		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topic.getId()).toUri(); // No Http quando devolvemos 201 ou created, precisamos devolver o cabeçalho do recurso criado e uma representação do que foi criado
		return ResponseEntity.created(uri).body(new TopicDto(topic)); // O UriComponentsBuilder ajudar a indicar o path criado e buildAndExpand vai settar dinamicamente o Id criado pra esse recurso
	}

	@GetMapping("/{id}")
	public ResponseEntity<TopicDetails> detailTopic(@PathVariable Long id) { //PathVariable diz que o id ira vir direto na URL. Não vai utilizar "?" vai vir direto após a barra

		Optional<Topic> topico = topicService.detail(id);

		if(topico.isPresent()) {
			return ResponseEntity.ok(new TopicDetails(topico.get()));
		}

		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "topicList", allEntries = true )
	public ResponseEntity<TopicDto> updateTopic(@PathVariable Long id, @RequestBody @Valid UpdateFormTopic form ) {

		Topic topic = topicService.updateTopic(id, form);
		
		return ResponseEntity.ok(new TopicDto(topic));
	}
	
	
	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "topicList", allEntries = true )
	public ResponseEntity<?> deleteTopic(@PathVariable Long id) {

		topicService.deleteById(id);

		 return ResponseEntity.ok().build();
	}
}
