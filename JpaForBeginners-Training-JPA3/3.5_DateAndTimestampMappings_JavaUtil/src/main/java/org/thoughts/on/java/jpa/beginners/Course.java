package org.thoughts.on.java.jpa.beginners;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Version;

@Entity
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_generator")
	@SequenceGenerator(name = "course_generator", sequenceName = "course_seq")
	private Long id;

	private String name;

	@Temporal(TemporalType.DATE)
	private Date startDate;

	@Temporal(TemporalType.TIME)
	private Date beginLecture;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date exam;

	@Version
	private long version;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getBeginLecture() {
		return beginLecture;
	}

	public void setBeginLecture(Date beginLecture) {
		this.beginLecture = beginLecture;
	}

	public Date getExam() {
		return exam;
	}

	public void setExam(Date exam) {
		this.exam = exam;
	}

	public Long getId() {
		return id;
	}

	public long getVersion() {
		return version;
	}

	@Override
	public String toString() {
		return "Course [id=" + id + ", name=" + name + ", startDate="
				+ startDate + ", beginLecture=" + beginLecture + ", exam="
				+ exam + ", version=" + version + "]";
	}

}
