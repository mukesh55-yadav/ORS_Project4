package in.co.rays.proj4.bean;


/**
 * @author Mukesh_Yadav
 * @version 1.0
 * @Copyright (c) SunilOS
 *
 */
public class CourseBean extends BaseBean {
	private String courseName;
	private String description;

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String getKey() {
		return id + "";
	}

	@Override
	public String getValue() {
		return courseName;
	}

}
