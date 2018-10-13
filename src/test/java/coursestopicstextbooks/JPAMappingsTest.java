package coursestopicstextbooks;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
public class JPAMappingsTest {
	
	
	@Resource
	private TestEntityManager entityManager;
	
	@Resource
	private TopicRepository topicRepo;
	
	@Resource
	private CourseRepository courseRepo;
	
	@Resource TextBookRepository textBookRepo;
	
	@Test
	public void shouldSaveAndLoadTopic() {
		Topic topic = topicRepo.save(new Topic("topic"));
		long topicId = topic.getId();
		
		entityManager.flush(); //force JPA to hit database when try to find
		entityManager.clear();
		
		Optional<Topic> result = topicRepo.findById(topicId);
		Topic underTest = result.get();
		assertThat(underTest.getName(), is("topic"));
	}
	
	@Test
	public void shouldGenerateTopicId() {
		Topic topic = topicRepo.save(new Topic("topic"));
		long topicId = topic.getId();
		
		entityManager.flush();
		entityManager.clear();
		
		assertThat(topicId, is(greaterThan(0L)));
	}
	
	@Test
	public void shouldSaveAndLoadCourse() {
		Course course = courseRepo.save(new Course("course", "description"));
		long courseId = course.getId();
		
		entityManager.flush(); //force JPA to hit database when try to find
		entityManager.clear();
		
		Optional<Course> result = courseRepo.findById(courseId);
		Course underTest = result.get();
		assertThat(underTest.getName(), is("course"));
	}
	
	@Test
	public void shouldEstablishCourseToTopicRelationship() {
		Topic java = topicRepo.save(new Topic("java"));
		Topic ruby = topicRepo.save(new Topic("ruby"));
		
		Course course = new Course("test Course", "description", java, ruby);
		course = courseRepo.save(course);
		long courseId = course.getId();
		
		entityManager.flush(); 
		entityManager.clear();
		
		Optional<Course> result = courseRepo.findById(courseId);
		Course underTest = result.get();
		assertThat(underTest.getTopics(), containsInAnyOrder(java, ruby));
	}
	
	@Test
	public void shouldFindCourseForTopic() {
		Topic java = topicRepo.save(new Topic("java"));
		
		Course course = courseRepo.save(new Course("course 1", "descr", java));
		Course course2 = courseRepo.save(new Course("course 2", "descr2", java));
		Course course3 = courseRepo.save(new Course("course 3", "descr2"));
		
		entityManager.flush(); 
		entityManager.clear();
		
		Collection<Course> coursesFoundForTopic = courseRepo.findByTopicsContains(java);
		assertThat(coursesFoundForTopic, containsInAnyOrder(course, course2));
	}

	@Test
	public void shouldFindCoursesForTopicId() {
		Topic java = topicRepo.save(new Topic("java"));
		long topicId = java.getId();
		
		Course course = courseRepo.save(new Course("course 1", "descr", java));
		Course course2 = courseRepo.save(new Course("course 2", "descr2", java));
		Course course3 = courseRepo.save(new Course("course 3", "descr2"));

		entityManager.flush(); 
		entityManager.clear();

		Collection<Course> coursesFoundForTopic = courseRepo.findByTopicsId(topicId);
		assertThat(coursesFoundForTopic, containsInAnyOrder(course, course2));
	}
	
	@Test
	public void shouldEstablishTextbookToCourseRelationship() {
		Course course = new Course("course 1", "descr");
		courseRepo.save(course);
		long courseId = course.getId();

		TextBook textBook = new TextBook("intro", course);
		TextBook textBook2 = new TextBook("intro2", course);

		textBookRepo.save(textBook);
		textBookRepo.save(textBook2);
		
		entityManager.flush(); 
		entityManager.clear();
		
		Optional<Course> result = courseRepo.findById(courseId);
		Course underTest = result.get();
		assertThat(underTest.getTextBooks(), containsInAnyOrder(textBook, textBook2));

	}
}
