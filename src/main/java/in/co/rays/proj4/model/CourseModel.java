package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.util.JDBCDataSource;

public class CourseModel {
	private static Logger log = Logger.getLogger(CourseModel.class);

	public long nextPk() throws SQLException {
		log.debug("next pk debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		long pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("select max(id) from st_course");
			pstm = conn.prepareStatement(sql.toString());
			rs = pstm.executeQuery();
			while (rs.next()) {
				pk = rs.getLong(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.commit();
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("next pk debug completed");
		return pk + 1;
	}

	public long add(CourseBean bean) throws SQLException, DuplicateRecordException {
		log.debug("add debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		long pk = nextPk();
		int i = 0;
		
		CourseBean duplicataCourse = findByCourseName(bean.getCourseName());
		// Check if create Role already exist
		if (duplicataCourse != null) {
			throw new DuplicateRecordException("Course already exist...!!!");
		}else {	
			try {
				conn = JDBCDataSource.getConnection();
				conn.setAutoCommit(false);
				StringBuffer sql = new StringBuffer("insert into st_course values (?,?,?,?,?,?,?)");
				pstm = conn.prepareStatement(sql.toString());
				pstm.setLong(1, pk);
				pstm.setString(2, bean.getCourseName());
				pstm.setString(3, bean.getDescription());
				pstm.setString(4, bean.getCreatedBy());
				pstm.setString(5, bean.getModifiedBy());
				pstm.setTimestamp(6, bean.getCreatedDatetime());
				pstm.setTimestamp(7, bean.getModifiedDatetime());
				i = pstm.executeUpdate();
				if (i > 0) {
					System.out.println("record inserted:" + i);
				} else {
					System.out.println("record not inserted");
				}
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

	public void delete(CourseBean bean) throws SQLException {
		log.debug("delete debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		int i = 0;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("delete from st_course where id=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, bean.getId());
			i = pstm.executeUpdate();
			if (i > 0) {
				System.out.println("data deleted successfully" + i);
			} else {
				System.out.println("data not deleted");
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

	public CourseBean findByCourseName(String courseName) throws SQLException {
		log.debug("findByCourseName debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CourseBean Bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("select * from st_course where coursename=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, courseName);
			rs = pstm.executeQuery();

			while (rs.next()) {
				Bean = new CourseBean();
				Bean.setId(rs.getLong(1));
				Bean.setCourseName(rs.getString(2));
				Bean.setDescription(rs.getString(3));
				Bean.setCreatedBy(rs.getString(4));
				Bean.setModifiedBy(rs.getString(5));
				Bean.setCreatedDatetime(rs.getTimestamp(6));
				Bean.setModifiedDatetime(rs.getTimestamp(7));
				return Bean;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.commit();
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("findByCourseName debug completed");
		return Bean;
	}

	public CourseBean findByPk(long pk) throws SQLException {
		log.debug("findbypk started");
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		CourseBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("select * from st_course where ID=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, pk);
			rs = pstm.executeQuery();

			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setCourseName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
				return bean;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.commit();
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("findbypk completed");
		return bean;
	}

	public void upadte(CourseBean bean) throws SQLException {
		log.debug(" model update started");
		Connection conn = null;
		PreparedStatement pstm = null;
		int i = 0;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer(
					"update st_course set coursename=?,description=?,created_by=?,modified_by=?,created_datetime=?,modified_datetime=? where id=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, bean.getCourseName());
			pstm.setString(2, bean.getDescription());
			pstm.setString(3, bean.getCreatedBy());
			pstm.setString(4, bean.getModifiedBy());
			pstm.setTimestamp(5, bean.getCreatedDatetime());
			pstm.setTimestamp(6, bean.getModifiedDatetime());
			pstm.setLong(7, bean.getId());
			i = pstm.executeUpdate();
			
			System.out.println("update ho gaya"+i);
			if (i > 0) {
				System.out.println("record updated:" + i);
			} else {
				System.out.println("record not updated");
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

	@SuppressWarnings("rawtypes")
	public List search(CourseBean bean) throws SQLException {
		return search(bean, 0, 0);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List search(CourseBean Bean, int pageNo, int pageSize) throws SQLException {
		log.debug("search started");
		ArrayList list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;

		StringBuffer sql = new StringBuffer("select * from st_course where 1=1"); 
		// dynamic injection return true value and at run time it will provide attribute to append with the query 
		if (Bean != null) {
			if (Bean.getId() > 0) {
				// System.out.println("hi model in if id");
				sql.append(" and ID=" + Bean.getId());
			}
			if (Bean.getCourseName() != null && Bean.getCourseName().length() > 0) {
				sql.append(" and coursename like '" + Bean.getCourseName() + "%'");
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
			CourseBean bean = null;
			while (rs.next()) {
				bean = new CourseBean();
				bean.setId(rs.getLong(1));
				bean.setCourseName(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setCreatedBy(rs.getString(4));
				bean.setModifiedBy(rs.getString(5));
				bean.setCreatedDatetime(rs.getTimestamp(6));
				bean.setModifiedDatetime(rs.getTimestamp(7));
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
		CourseBean Bean = null;
		PreparedStatement pstm = null;
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_course ");

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
				Bean = new CourseBean();
				Bean.setId(rs.getLong(1));
				Bean.setCourseName(rs.getString(2));
				Bean.setDescription(rs.getString(3));
				Bean.setCreatedBy(rs.getString(4));		
				Bean.setModifiedBy(rs.getString(5));
				Bean.setCreatedDatetime(rs.getTimestamp(6));
				Bean.setModifiedDatetime(rs.getTimestamp(7));
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
