package br.com.alura.forum.service;

import br.com.alura.forum.controller.dto.TopicDto;
import br.com.alura.forum.controller.form.FormTopics;
import br.com.alura.forum.controller.form.UpdateFormTopic;
import br.com.alura.forum.model.Topic;
import br.com.alura.forum.repository.CourseRepository;
import br.com.alura.forum.repository.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TopicService {

    TopicRepository topicRepository;
    CourseRepository courseRepository;

    @Autowired
    public TopicService(TopicRepository topicRepository, CourseRepository courseRepository) {
        this.topicRepository = topicRepository;
        this.courseRepository = courseRepository;
    }

    public Page<TopicDto> findTopic(Pageable pagination, String courseName) {

        if (courseName == null) {
            Page<Topic> topics = topicRepository.findAll(pagination);
            return TopicDto.converter(topics);
        } else {
            Page<Topic> topics = topicRepository.findByCourse_Name(courseName, pagination);// Para navegar e trazer o filtro colocamos o nome do atributo. Como é um relacionamento colocamos o Underline para ele navegar pelas classes
            return TopicDto.converter(topics);
        }
    }

    public Topic register(FormTopics input){
        Topic topic = input.convert(courseRepository);
        topicRepository.save(topic);

        return topic;
    }

    public Optional<Topic> detail(Long id) {
        return topicRepository.findById(id);
    }

    public Topic updateTopic(Long id, UpdateFormTopic form) {
        return form.update(id, topicRepository);
    }

    public void deleteById(Long id) {
        topicRepository.deleteById(id);
    }
}
