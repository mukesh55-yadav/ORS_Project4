package in.co.rays.proj4.bean;

import java.util.Date;

public class TimeTableBean extends BaseBean {
	private String courseName;
	private long courseId;
	private String subjectName;
	private long SubjectId;
	private Date examDate;
	private String examTime;
	private int semester;

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public long getSubjectId() {
		return SubjectId;
	}

	public void setSubjectId(long subjectId) {
		SubjectId = subjectId;
	}

	public Date getExamDate() {
		return examDate;
	}

	public void setExamDate(Date examDate) {
		this.examDate = examDate;
	}

	public String getExamTime() {
		return examTime;
	}

	public void setExamTime(String examTime) {
		this.examTime = examTime;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	@Override
	public String getKey() {
		return id + "";
	}

	@Override
	public String getValue() {
		return courseName + " " + subjectName;
	}

}
