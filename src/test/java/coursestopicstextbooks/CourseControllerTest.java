package coursestopicstextbooks;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

public class CourseControllerTest {

	@InjectMocks
	private CourseController underTest;
	
	@Mock
	private Course course;
	
	@Mock
	private Course course2;
	
	@Mock
	private CourseRepository courseRepo;
	
	@Mock
	private TopicRepository topicRepo;
	
	@Mock
	private Topic topic;
	
	@Mock
	private Topic topic2;
	
	@Mock
	private TextBookRepository textBookRepo;
	
	@Mock
	private TextBook book1;
	
	@Mock
	private TextBook book2;
	
	@Mock
	private Model model;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void addSingleCourseToModel() throws Exception{
		long arbitraryCourseId = 1;
		when(courseRepo.findById(arbitraryCourseId)).thenReturn(Optional.of(course));
		
		underTest.findOneCourse(arbitraryCourseId, model);
		verify(model).addAttribute("courses", course);
	}
	
	@Test
	public void addAllCoursesToModel() {
		Collection<Course> allCourses = asList(course, course2);
		when(courseRepo.findAll()).thenReturn(allCourses);
		
		underTest.findAllCourses(model);
		verify(model).addAttribute("courses", allCourses);
	}
	
	@Test
	public void addSingleTopicToModel() throws Exception{
		long arbitraryId = 1;
		when(topicRepo.findById(arbitraryId)).thenReturn(Optional.of(topic));
		
		underTest.findOneTopic(arbitraryId, model);
		verify(model).addAttribute("topics", topic);
	}
	
	@Test
	public void addAllTopicsToModel() {
		Collection<Topic> allTopics = asList(topic, topic2);
		when(topicRepo.findAll()).thenReturn(allTopics);
		
		underTest.findAllTopics(model);
		verify(model).addAttribute("topics", allTopics);
	}
	
	@Test
	public void addSingleTextBookToModel() throws Exception {
		long arbitraryId = 1;
		when(textBookRepo.findById(arbitraryId)).thenReturn(Optional.of(book1));
		
		underTest.findOneTextBook(arbitraryId, model);
		verify(model).addAttribute("textBooks", book1);
	}
	
	@Test
	public void allAllTextBooksToModel() {
		Collection<TextBook> allTextBooks = asList(book1, book2);
		when(textBookRepo.findAll()).thenReturn(allTextBooks);
		
		underTest.findAllTextBooks(model);
		verify(model).addAttribute("textBooks", allTextBooks);
	}
	
	
}
