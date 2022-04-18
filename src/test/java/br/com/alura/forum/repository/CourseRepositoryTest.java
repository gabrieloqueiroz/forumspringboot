package br.com.alura.forum.repository;

import br.com.alura.forum.model.Course;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CourseRepositoryTest {

    private CourseRepository courseRepository;

    @Autowired
    public CourseRepositoryTest(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Test
    public void should_return_course_when_find_by_name(){
        //Given
        String courseName = "HTML 5";

        //When
        Course response = courseRepository.findByName(courseName);

        //Then
        Assertions.assertNotNull(response);
        Assertions.assertEquals(courseName, response.getName());
    }

    @Test
    public void not_should_return_course_when_find_by_name_not_exist(){
        //Given
        String courseName = "JPA";

        //When
        Course response = courseRepository.findByName(courseName);

        //Then
        Assertions.assertNull(response);
    }
}
