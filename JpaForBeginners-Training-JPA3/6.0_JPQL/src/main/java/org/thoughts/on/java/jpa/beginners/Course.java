package org.thoughts.on.java.jpa.beginners;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;

@NamedQuery(
		name = Course.FIND_ALL_COURSES, 
		query = "SELECT c FROM Course c")
@Entity
public class Course {

	public static final String FIND_ALL_COURSES = "findAllCourses";
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_generator")
	@SequenceGenerator(name = "course_generator", sequenceName = "course_seq")
	private Long id;

	private String name;

	private LocalDate startDate;

	private LocalDate endDate;

	@ManyToOne(fetch = FetchType.LAZY)
	private Professor professor;

	@Version
	private long version;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Long getId() {
		return id;
	}

	public long getVersion() {
		return version;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", startDate="
				+ startDate + ", endDate=" + endDate + ", version=" + version
				+ "]";
	}

}
