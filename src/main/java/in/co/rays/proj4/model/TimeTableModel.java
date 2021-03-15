package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.bean.TimeTableBean;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.JDBCDataSource;

@SuppressWarnings("unused")
public class TimeTableModel {
	public static Logger log = Logger.getLogger(TimeTableModel.class);

	public Integer nextPk() throws SQLException {
		log.debug("nextpk debug started");
		int pk = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Statement st = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			st = conn.createStatement();
			rs = st.executeQuery("select max(id) from st_timetable");
			while (rs.next()) {
				pk = rs.getInt(1);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			st.close();
			conn.commit();
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("nextPk debug completed");
		return pk + 1;
	}

	public long add(TimeTableBean bean) throws DuplicateRecordException, SQLException, RecordNotFoundException {
		log.debug("add debug started");
		int pk = nextPk();
		int i = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Statement st = null;
		// Add course name from course id for preload//

		CourseModel couModel = new CourseModel();
		CourseBean couBean = new CourseBean();
		couBean = couModel.findByPk(bean.getCourseId());
		String couName = couBean.getCourseName();

		// Add Subject Name by Subject id for preload//
		SubjectModel subModel = new SubjectModel();
		SubjectBean subBean = new SubjectBean();
		subBean = subModel.findByPk(bean.getSubjectId());
		String subName = subBean.getSubjectName();

		TimeTableBean Bean = new TimeTableBean();
		Bean = findByCourseNameSubjectName(couName, subName);
		if (Bean != null) {
			throw new DuplicateRecordException("record already existed");
		}
		Bean = findByCourseNameExamDate(couName, bean.getExamDate());
		if (Bean != null) {
			throw new DuplicateRecordException("record already existed");
		}

		Bean = findByCourseNameSubjectNameExamDateExamTime(couName, subName, bean.getExamDate(), bean.getExamTime());
		if (Bean != null) {
			throw new DuplicateRecordException("record already existed");
		} else {
			try {
				conn = JDBCDataSource.getConnection();
				conn.setAutoCommit(false);
				StringBuffer sql = new StringBuffer("insert into st_timetable values (?,?,?,?,?,?,?,?,?,?,?,?)");
				pstm = conn.prepareStatement(sql.toString());
				pstm.setInt(1, pk);
				pstm.setString(2, couName);
				pstm.setLong(3, bean.getCourseId());
				pstm.setString(4, subName);
				pstm.setLong(5, bean.getSubjectId());
				pstm.setDate(6, new java.sql.Date(bean.getExamDate().getTime()));
				pstm.setString(7, bean.getExamTime());
				pstm.setInt(8, bean.getSemester());
				pstm.setString(9, bean.getCreatedBy());
				pstm.setString(10, bean.getModifiedBy());
				pstm.setTimestamp(11, bean.getCreatedDatetime());
				pstm.setTimestamp(12, bean.getModifiedDatetime());
				i = pstm.executeUpdate();
				return i;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				pstm.close();
				conn.commit();
				JDBCDataSource.closeConnection(conn);
			}

		}
		log.debug("add debug completed");
		return i;
	}

	public void delete(TimeTableBean bean) throws Exception {
		log.debug("delete debug started");
		PreparedStatement pstm = null;
		Connection conn = null;
		ResultSet rs = null;
		int i = 0;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("delete from st_timetable where id=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, bean.getId());
			i = pstm.executeUpdate();
			if (i > 0) {
				System.out.println("data deleted successfully");
			} else {
				throw new DatabaseException("data not deleted");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.commit();
			JDBCDataSource.closeConnection(conn);
		}

		log.debug("delete debug completed");
	}

	public TimeTableBean findByCourseNameSubjectName(String courseName, String subjectName)
			throws SQLException, RecordNotFoundException {
		log.debug("findByCourseNameSubjectName debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		TimeTableBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("select * from st_timetable where course_name=? and subject_name=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, courseName);
			pstm.setString(2, subjectName);
			rs = pstm.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseName(rs.getString(2));
				bean.setCourseId(rs.getLong(3));
				bean.setSubjectName(rs.getString(4));
				bean.setSubjectId(rs.getLong(5));
				bean.setExamDate(rs.getDate(6));
				bean.setExamTime(rs.getString(7));
				bean.setSemester(rs.getInt(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				return bean;
			}
		} catch (Exception e) {
			e.printStackTrace();
			// throw new RecordNotFoundException("no such record exist");
		} finally {
			pstm.close();
			conn.commit();
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("findByCourseNameSubjectName debug completed");
		return bean;
	}

	public TimeTableBean findByCourseNameExamDate(String courseName, Date examDate) throws SQLException {
		log.debug("findByCourseNameDate debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		TimeTableBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("select * from st_timetable where course_name=? and exam_date=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, courseName);
			pstm.setDate(2, new java.sql.Date(examDate.getTime())); // doubt
			rs = pstm.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseName(rs.getString(2));
				bean.setCourseId(rs.getLong(3));
				bean.setSubjectName(rs.getString(4));
				bean.setSubjectId(rs.getLong(5));
				bean.setExamDate(rs.getDate(6));
				bean.setExamTime(rs.getString(7));
				bean.setSemester(rs.getInt(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				return bean;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.commit();
			JDBCDataSource.closeConnection(conn);

		}
		log.debug("findByCourseNameDate debug completed");
		return bean;
	}

	public TimeTableBean findByCourseNameSubjectNameExamDateExamTime(String courseName, String subjectName,
			Date exameDate, String examTime) throws SQLException {
		log.debug("findByCourseNameSubjectNameDateTime debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		TimeTableBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer(
					"select * from st_timetable where course_name=? and subject_name=? and exam_date=? and exam_time=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, courseName);
			pstm.setString(2, subjectName);
			pstm.setDate(3, new java.sql.Date(exameDate.getTime()));
			pstm.setString(4, examTime);
			rs = pstm.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseName(rs.getString(2));
				bean.setCourseId(rs.getLong(3));
				bean.setSubjectName(rs.getString(4));
				bean.setSubjectId(rs.getLong(5));
				bean.setExamDate(rs.getDate(6));
				bean.setExamTime(rs.getString(7));
				bean.setSemester(rs.getInt(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.commit();
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("findByCourseNameSubjectNameDateTime debug completed");
		// System.out.println(bean==null);
		return bean;
	}

	public TimeTableBean findByPk(long pk) throws SQLException {
		log.debug("findByPk debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		TimeTableBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("select * from st_timetable where id=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, pk);
			rs = pstm.executeQuery();
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseName(rs.getString(2));
				bean.setCourseId(rs.getLong(3));
				bean.setSubjectName(rs.getString(4));
				bean.setSubjectId(rs.getLong(5));
				bean.setExamDate(rs.getDate(6));
				bean.setExamTime(rs.getString(7));
				bean.setSemester(rs.getInt(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				return bean;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.commit();
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("findByPk debug completed");
		return bean;
	}

	public void update(TimeTableBean bean) throws SQLException, DuplicateRecordException, RecordNotFoundException {
		log.debug("update debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int i = 0;
		// Add course name from course id for preload//

		CourseModel couModel = new CourseModel();
		CourseBean couBean = new CourseBean();
		couBean = couModel.findByPk(bean.getCourseId());
		String couName = couBean.getCourseName();

		// Add Subject Name by Subject id for preload//

		SubjectModel subModel = new SubjectModel();
		SubjectBean subBean = new SubjectBean();
		subBean = subModel.findByPk(bean.getSubjectId());
		String subName = subBean.getSubjectName();

		TimeTableBean Bean1 = new TimeTableBean();
		TimeTableBean Bean2 = new TimeTableBean();
		TimeTableBean Bean3 = new TimeTableBean();

		Bean1 = findByCourseNameSubjectNameExamDateExamTime(couName, subName, bean.getExamDate(), bean.getExamTime());

		if (Bean1 != null && Bean1.getId() != bean.getId()) {
			throw new DuplicateRecordException(
					"course name and subject name and exam date and exam timerecord already existed");
		}

		Bean2 = findByCourseNameExamDate(couName, bean.getExamDate());
		if (Bean2 != null && Bean2.getId() != bean.getId()) {
			throw new DuplicateRecordException("course name and exam date already existed");
		}

		Bean3 = findByCourseNameSubjectName(couName, subName);

		if (Bean3 != null && Bean3.getId() != bean.getId()) {
			throw new DuplicateRecordException("course name and subject name already existed");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer(
					"update st_timetable set course_name=?,course_id=?,subject_name=?,subject_id=?,exam_date=?,exam_time=?,semester=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, couName);
			pstm.setLong(2, bean.getCourseId());
			pstm.setString(3, subName);
			pstm.setLong(4, bean.getSubjectId());
			pstm.setDate(5, new java.sql.Date(bean.getExamDate().getTime()));
			pstm.setString(6, bean.getExamTime());
			pstm.setInt(7, bean.getSemester());
			pstm.setString(8, bean.getCreatedBy());
			pstm.setString(9, bean.getModifiedBy());
			pstm.setTimestamp(10, bean.getCreatedDatetime());
			pstm.setTimestamp(11, bean.getModifiedDatetime());
			pstm.setLong(12, bean.getId());
			i = pstm.executeUpdate();
			if (i > 0) {
				System.out.println("record updated in database");
			} else {
				System.out.println("record updation unsuccessful");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.commit();
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("update debug completed");
	}

	@SuppressWarnings("rawtypes")
	public List search(TimeTableBean bean) throws SQLException {
		return search(bean, 0, 0);
	}

	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	public List search(TimeTableBean Bean, int pageNo, int pageSize) throws SQLException {
		log.debug("search debug started");
		ArrayList list = new ArrayList();
		Connection conn = null;
		StringBuffer sql = new StringBuffer("select * from st_timetable where 1=1"); 
		//dynamic injection return true value and at run time it will provide attribute to append with the query 
		
		if (Bean != null) {
			if (Bean.getId() > 0) {
				sql.append(" and ID=" + Bean.getId());
			}

			// System.out.println(Bean.getCourseName());

			if (Bean.getCourseName() != null && Bean.getCourseName().length() > 0) {
				sql.append(" and course_name like '" + Bean.getCourseName() + "%'");
			}

			// System.out.println(Bean.getSubjectName());

			if (Bean.getSubjectName() != null && Bean.getSubjectName().length() > 0) {
				sql.append(" and subject_name like'" + Bean.getSubjectName() + "%'");
			}

			System.out.println("exam date" + Bean.getExamDate());

			if (Bean.getExamDate() != null && Bean.getExamDate().getDate() > 0) {
				sql.append(" and exam_date = '" + DataUtility.getSearchDate(Bean.getExamDate()) + "'");
			}

			if (Bean.getExamTime() != null && Bean.getExamTime().length() > 0) {
				sql.append(" and exam_time like'" + Bean.getExamTime() + "%'");
			}
			if (Bean.getSemester() > 0) {
				sql.append(" and semester=" + Bean.getSemester());
			}
			if (Bean.getCourseId() > 0) {
				sql.append(" and course_id=" + Bean.getCourseId());
			}
			// System.out.println(Bean.getSubjectId());
			if (Bean.getSubjectId() > 0) {
				sql.append(" and subject_id=" + Bean.getSubjectId());
			}

		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		try {
			PreparedStatement pstm = null;
			ResultSet rs = null;
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			pstm = conn.prepareStatement(sql.toString());
			rs = pstm.executeQuery();
			TimeTableBean bean = null;
			while (rs.next()) {
				bean = new TimeTableBean();
				bean.setId(rs.getLong(1));
				bean.setCourseName(rs.getString(2));
				bean.setCourseId(rs.getLong(3));
				bean.setSubjectName(rs.getString(4));
				bean.setSubjectId(rs.getLong(5));
				bean.setExamDate(rs.getDate(6));
				bean.setExamTime(rs.getString(7));
				bean.setSemester(rs.getInt(8));
				bean.setCreatedBy(rs.getString(9));
				bean.setModifiedBy(rs.getString(10));
				bean.setCreatedDatetime(rs.getTimestamp(11));
				bean.setModifiedDatetime(rs.getTimestamp(12));
				list.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			conn.commit();
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("search debug completed");
		return list;
	}

	@SuppressWarnings("rawtypes")
	public List list() throws SQLException {
		return list(0, 0);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List list(int pageNo, int pageSize) throws SQLException {
		log.debug("list debug started");
		Connection conn = null;
		TimeTableBean Bean = null;
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_timetable ");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append("limit " + pageNo + "," + pageSize);
		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstm = conn.prepareStatement(sql.toString());
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				Bean = new TimeTableBean();
				Bean.setId(rs.getLong(1));
				Bean.setCourseName(rs.getString(2));
				Bean.setCourseId(rs.getLong(3));
				Bean.setSubjectName(rs.getString(4));
				Bean.setSubjectId(rs.getLong(5));
				Bean.setExamDate(rs.getDate(6));
				Bean.setExamTime(rs.getString(7));
				Bean.setSemester(rs.getInt(8));
				Bean.setCreatedBy(rs.getString(9));
				Bean.setModifiedBy(rs.getString(10));
				Bean.setCreatedDatetime(rs.getTimestamp(11));
				Bean.setModifiedDatetime(rs.getTimestamp(12));
				list.add(Bean);
			}
		} catch (Exception e) {
			// throw new RecordNotFoundException("exception in list search");
			e.printStackTrace();
		} finally {

			conn.commit();
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("list debug completed");
		return list;
	}

}
