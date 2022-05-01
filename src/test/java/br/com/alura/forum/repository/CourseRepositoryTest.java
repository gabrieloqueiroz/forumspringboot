package br.com.alura.forum.repository;

import br.com.alura.forum.model.Course;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class CourseRepositoryTest {

    private CourseRepository courseRepository;
    private TestEntityManager testEntityManager;

    @Autowired
    public CourseRepositoryTest(CourseRepository courseRepository, TestEntityManager testEntityManager) {
        this.courseRepository = courseRepository;
        this.testEntityManager = testEntityManager;
    }

    @Test
    public void should_return_course_when_find_by_name(){
        //Given
        String courseName = "HTML 5";

        Course html5 = new Course();
        html5.setName(courseName);
        html5.setCategory("development");
        testEntityManager.persist(html5);

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