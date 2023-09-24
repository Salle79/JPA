package org.thoughts.on.java.jpa.beginners;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Version;

@Entity
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_generator")
	@SequenceGenerator(name = "course_generator", sequenceName = "course_seq")
	private Long id;

	private String name;

	private LocalDate startDate;

	private LocalTime beginLecture;
	
	private LocalDateTime exam;

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

	public LocalTime getBeginLecture() {
		return beginLecture;
	}

	public void setBeginLecture(LocalTime beginLecture) {
		this.beginLecture = beginLecture;
	}

	public Long getId() {
		return id;
	}

	public long getVersion() {
		return version;
	}

	public LocalDateTime getExam() {
		return exam;
	}

	public void setExam(LocalDateTime exam) {
		this.exam = exam;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", startDate="
				+ startDate + ", beginLecture=" + beginLecture + ", exam="
				+ exam + ", version=" + version + "]";
	}

	

}
