package coursestopicstextbooks;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TextBook {

	@Id
	@GeneratedValue
	private long id;
	
	private String title;
	
	@ManyToOne
	private Course course;
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return title;
	}
	
	public Course getCourse() {
		return course;
	}
	
	public TextBook() {}

	public TextBook(String title, Course course) {
		this.title = title;
		this.course = course;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TextBook other = (TextBook) obj;
		if (id != other.id)
			return false;
		return true;
	}

	
}
