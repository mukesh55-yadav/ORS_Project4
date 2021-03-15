package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.util.JDBCDataSource;

public class SubjectModel {
	private static Logger log = Logger.getLogger(StudentModel.class);

	public Integer nextPk() throws SQLException {
		log.debug("next pk debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("select max(id) from st_subject");
			pstm = conn.prepareStatement(sql.toString());
			rs = pstm.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.commit();
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("next pk completed");
		return pk + 1;
	}

	public long add(SubjectBean bean) throws SQLException, DuplicateRecordException, RecordNotFoundException {
		log.debug("add debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		int pk = nextPk();
		long i = 0;
		// Add of course name with course id for prload//
		CourseModel couModel = new CourseModel();

		CourseBean couBean = couModel.findByPk(bean.getCourseId());

		String courseName = couBean.getCourseName();
		
		SubjectBean duplicataSub = findBySubjectName(bean.getSubjectName());
		// Check if create Role already exist
		if (duplicataSub != null) {
			throw new DuplicateRecordException("Subject Name already exist...!!!");
		}else {
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("insert into st_subject values (?,?,?,?,?,?,?,?,?,?)");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, pk);
			pstm.setString(2, courseName);
			pstm.setLong(3, bean.getCourseId());
			pstm.setString(4, bean.getSubjectName());
			pstm.setLong(5, bean.getSubjectId());
			pstm.setString(6, bean.getDescription());
			pstm.setString(7, bean.getCreatedBy());
			pstm.setString(8, bean.getModifiedBy());
			pstm.setTimestamp(9, bean.getCreatedDatetime());
			pstm.setTimestamp(10, bean.getModifiedDatetime());
			i = pstm.executeUpdate();
			if (i > 0) {
				System.out.println("record added at:" + i);
			} else {
				System.out.println("add unsuccessful");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.commit();
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("add debug completed");
		return i;
		}
	}

	public void delete(SubjectBean bean) throws SQLException {
		log.debug("model delete started");
		Connection conn = null;
		PreparedStatement pstm = null;
		int i = 0;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("delete from st_subject where id=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, bean.getId());
			i = pstm.executeUpdate();
			if (i > 0) {
				System.out.println("deleted:" + i);
			} else {
				System.out.println("data not deleted:" + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.commit();
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("model delete completed");

	}

	public void update(SubjectBean bean) throws SQLException, DuplicateRecordException, RecordNotFoundException {
		log.debug("update started");
		Connection conn = null;
		PreparedStatement pstm = null;
		int i = 0;
		/////// update of course name with course id for prload/////
		CourseModel couModel = new CourseModel();

		CourseBean couBean = couModel.findByPk(bean.getCourseId());

		String courseName = couBean.getCourseName();
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer(
					"update st_subject set course_name=?,course_id=?,subject_name=?,subject_id=?,description=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, courseName);
			pstm.setLong(2, bean.getCourseId());
			pstm.setString(3, bean.getSubjectName());
			pstm.setLong(4, bean.getSubjectId());
			pstm.setString(5, bean.getDescription());
			pstm.setString(6, bean.getCreatedBy());
			pstm.setString(7, bean.getModifiedBy());
			pstm.setTimestamp(8, bean.getCreatedDatetime());
			pstm.setTimestamp(9, bean.getModifiedDatetime());
			pstm.setLong(10, bean.getId());
			i = pstm.executeUpdate();
			if (i > 0) {
				System.out.println("record updated:" + i);
			} else {
				System.out.println("record not updated:" + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.commit();
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("update completed");
	}

	public SubjectBean findBySubjectName(String subjectName) throws SQLException {
		log.debug("findBySubjectName debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		SubjectBean Bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("select * from st_subject where subject_name=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, subjectName);
			rs = pstm.executeQuery();

			while (rs.next()) {
				Bean = new SubjectBean();
				Bean.setId(rs.getLong(1));
				Bean.setCourseName(rs.getString(2));
				Bean.setCourseId(rs.getLong(3));
				Bean.setSubjectName(rs.getString(4));
				Bean.setSubjectId(rs.getLong(5));
				Bean.setDescription(rs.getString(6));
				Bean.setCreatedBy(rs.getString(7));
				Bean.setModifiedBy(rs.getString(8));
				Bean.setCreatedDatetime(rs.getTimestamp(9));
				Bean.setModifiedDatetime(rs.getTimestamp(10));
				return Bean;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.commit();
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("findBySubjectName debug completed");
		return Bean;
	}

	public SubjectBean findByPk(long pk) throws SQLException {
		log.debug("findByPk debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		SubjectBean Bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("select * from st_subject where id=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, pk);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Bean = new SubjectBean();
				Bean.setId(rs.getLong(1));
				Bean.setCourseName(rs.getString(2));
				Bean.setCourseId(rs.getLong(3));
				Bean.setSubjectName(rs.getString(4));
				Bean.setSubjectId(rs.getLong(5));
				Bean.setDescription(rs.getString(6));
				Bean.setCreatedBy(rs.getString(7));
				Bean.setModifiedBy(rs.getString(8));
				Bean.setCreatedDatetime(rs.getTimestamp(9));
				Bean.setModifiedDatetime(rs.getTimestamp(10));
				return Bean;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.commit();
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("find by pk completed");
		return Bean;
	}

	@SuppressWarnings("rawtypes")
	public List search(SubjectBean bean) throws SQLException {
		return search(bean, 0, 0);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List search(SubjectBean Bean, int pageNo, int pageSize) throws SQLException {
		log.debug("search started");
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		/////// search of course name with course id for prload/////
		/*
		 * CourseModel couModel=new CourseModel();
		 * 
		 * CourseBean couBean=couModel.findByPk(Bean.getCourseId());
		 * 
		 * String courseName=couBean.getCourseName();
		 */

		StringBuffer sql = new StringBuffer("select * from st_subject where 1=1"); 
		if (Bean != null) {
			if (Bean.getId() > 0) {
				sql.append(" and ID=" + Bean.getId());
			}
			if (Bean.getCourseName() != null && Bean.getCourseName().length() > 0) {
				sql.append(" and course_name like '" + Bean.getCourseName() + "%'");
			}
			if (Bean.getCourseId() > 0) {
				sql.append(" and course_id=" + Bean.getCourseId());
			}
			if (Bean.getSubjectName() != null && Bean.getSubjectName().length() > 0) {
				sql.append(" and subject_name like '" + Bean.getSubjectName() + "%'");
			}
			if (Bean.getSubjectId() > 0) {
				sql.append(" and subject_id=" + Bean.getSubjectId());
			}
		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			pstm = conn.prepareStatement(sql.toString());
			rs = pstm.executeQuery();
			SubjectBean bean = null;
			while (rs.next()) {
				bean = new SubjectBean();
				bean.setId(rs.getLong(1));
				bean.setCourseName(rs.getString(2));
				bean.setCourseId(rs.getLong(3));
				bean.setSubjectName(rs.getString(4));
				bean.setSubjectId(rs.getLong(5));
				bean.setDescription(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				list.add(bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.commit();
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("search completed");
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
		SubjectBean Bean = null;
		PreparedStatement pstm = null;
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_subject ");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append("limit " + pageNo + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			pstm = conn.prepareStatement(sql.toString());
			ResultSet rs = pstm.executeQuery();
			while (rs.next()) {
				Bean = new SubjectBean();
				Bean.setId(rs.getLong(1));
				Bean.setCourseName(rs.getString(2));
				Bean.setCourseId(rs.getLong(3));
				Bean.setSubjectName(rs.getString(4));
				Bean.setSubjectId(rs.getLong(5));
				Bean.setDescription(rs.getString(6));
				Bean.setCreatedBy(rs.getString(7));
				Bean.setModifiedBy(rs.getString(8));
				Bean.setCreatedDatetime(rs.getTimestamp(9));
				Bean.setModifiedDatetime(rs.getTimestamp(10));
				list.add(Bean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.commit();
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("list debug completed");

		return list;

	}

}
