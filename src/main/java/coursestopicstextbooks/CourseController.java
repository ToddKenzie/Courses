package coursestopicstextbooks;

import java.util.Optional;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CourseController {

	@Resource
	private CourseRepository courseRepo;
	
	@Resource
	private TopicRepository topicRepo;
	
	@Resource
	private TextBookRepository textBookRepo;

	@RequestMapping("/course")
	public String findOneCourse(@RequestParam(value="id")long id, Model model) throws Exception{
		Optional<Course> course = courseRepo.findById(id);
		
		if(course.isPresent()) {
			model.addAttribute("courses", course.get());
			return "course";
		}
		throw new CourseNotFoundException();
	}

	@RequestMapping("/allcourses")
	public String findAllCourses(Model model) {
		model.addAttribute("courses", courseRepo.findAll());
		return "courses";
	}

	@RequestMapping("/topic")
	public String findOneTopic(@RequestParam(value="id")long id, Model model) throws Exception {
		Optional<Topic> topic = topicRepo.findById(id);
		
		if (topic.isPresent()) {
			model.addAttribute("topics", topic.get());
			model.addAttribute("courses", courseRepo.findByTopicsContains(topic.get()));
			return "topic";
		}
		throw new TopicNotFoundException();
	}

	@RequestMapping("/allTopics")
	public String findAllTopics(Model model) {
		model.addAttribute("topics", topicRepo.findAll());
		return "topics";
	}

	@RequestMapping("/allTextBooks")
	public String findAllTextBooks(Model model) {
		model.addAttribute("textBooks", textBookRepo.findAll());
		return "textBooks";
	}

	@RequestMapping("/textBook")
	public String findOneTextBook(long id, Model model) throws Exception{
		Optional<TextBook> textBook = textBookRepo.findById(id);
		
		if (textBook.isPresent()) {
			model.addAttribute("textBooks", textBook.get());
			model.addAttribute("courses", courseRepo.findByTextBookContains(textBook.get()));
			return "textBooks";
		}
		throw new TextBookNotFoundException();
		
	}

}
