package coursestopicstextbooks;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, Long> {

	Collection<Course> findByTopicsContains(Topic topic);

	Collection<Course> findByTopicsId(Long topicId);
	
	Collection<Course> findByTextBookContains(TextBook textBook);


}
