package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.bean.FacultyBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.util.JDBCDataSource;

public class FacultyModel {
	public static Logger log = Logger.getLogger(FacultyModel.class);

	@SuppressWarnings("unused")
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
			rs = st.executeQuery("select max(id) from st_faculty");
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

	@SuppressWarnings("unused")
	public long add(FacultyBean bean) throws SQLException, DuplicateRecordException, ApplicationException {
		log.debug("add debug started");
		int pk = nextPk();
		int i = 0;
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		Statement st = null;
		FacultyBean Bean = new FacultyBean();

		/////////// Add college name by college id in data base///////////
		CollegeModel collegeModel = new CollegeModel();
		CollegeBean collegeBean = new CollegeBean();
		collegeBean = collegeModel.findByPK(bean.getCollegeId());
		String collegeName = collegeBean.getName();

		//////////////// Update subject Name by subject id in data base//////
		SubjectModel subModel = new SubjectModel();
		SubjectBean subBean = new SubjectBean();
		subBean = subModel.findByPk(bean.getSubjectId());
		String subName = subBean.getSubjectName();

		Bean = findByLoginId(bean.getLoginId());
		if (Bean != null) {
			throw new DuplicateRecordException("Faculty Login already exist..!!! ");
		} else {
			try {
				conn = JDBCDataSource.getConnection();
				conn.setAutoCommit(false);
				StringBuffer sql = new StringBuffer("insert into st_faculty values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				pstm = conn.prepareStatement(sql.toString());
				pstm.setLong(1, pk);
				pstm.setString(2, bean.getFirstName());
				pstm.setString(3, bean.getLastName());
				pstm.setString(4, bean.getLoginId());
				pstm.setDate(5, new java.sql.Date(bean.getDoj().getTime()));
				pstm.setString(6, bean.getMobileNo());
				pstm.setString(7, collegeName);
				pstm.setLong(8, bean.getCollegeId());
				pstm.setString(9, subName);
				pstm.setLong(10, bean.getSubjectId());
				pstm.setString(11, bean.getCreatedBy());
				pstm.setString(12, bean.getModifiedBy());
				pstm.setTimestamp(13, bean.getCreatedDatetime());
				pstm.setTimestamp(14, bean.getModifiedDatetime());
				i = pstm.executeUpdate();
				if (i > 0) {
					System.out.println("record added no" + i);
				} else {
					System.out.println("record not added");
				}
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

	@SuppressWarnings("unused")
	public void delete(FacultyBean bean) throws SQLException {
		log.debug("delete debug started");
		PreparedStatement pstm = null;
		Connection conn = null;
		ResultSet rs = null;
		int i = 0;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("delete from st_faculty where id=?");
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

	public FacultyBean findByLoginId(String loginId) throws SQLException {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		FacultyBean Bean = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("select * from st_faculty where LOGIN_ID=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, loginId);
			rs = pstm.executeQuery();
			while (rs.next()) {
				Bean = new FacultyBean();
				Bean.setId(rs.getLong(1));
				Bean.setFirstName(rs.getString(2));
				Bean.setLastName(rs.getString(3));
				Bean.setLoginId(rs.getString(4));
				Bean.setDoj(rs.getDate(5));
				Bean.setMobileNo(rs.getString(6));
				Bean.setCollegeName(rs.getString(7));
				Bean.setCollegeId(rs.getLong(8));
				Bean.setSubjectName(rs.getString(9));
				Bean.setSubjectId(rs.getLong(10));
				Bean.setCreatedBy(rs.getString(11));
				Bean.setModifiedBy(rs.getString(12));
				Bean.setCreatedDatetime(rs.getTimestamp(13));

				Bean.setModifiedDatetime(rs.getTimestamp(14));
				return Bean;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.commit();
			JDBCDataSource.closeConnection(conn);
		}
		return Bean;

	}

	public FacultyBean findByPk(long pk) throws SQLException {
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		FacultyBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer("select * from st_faculty where ID=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setLong(1, pk);
			rs = pstm.executeQuery();
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLoginId(rs.getString(4));
				bean.setDoj(rs.getDate(5));
				bean.setMobileNo(rs.getString(6));
				bean.setCollegeName(rs.getString(7));
				bean.setCollegeId(rs.getLong(8));
				bean.setSubjectName(rs.getString(9));
				bean.setSubjectId(rs.getLong(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDatetime(rs.getTimestamp(13));
				bean.setModifiedDatetime(rs.getTimestamp(14));
				return bean;
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.commit();
			JDBCDataSource.closeConnection(conn);

		}
		return bean;
	}

	@SuppressWarnings("unused")
	public void update(FacultyBean bean) throws DuplicateRecordException, SQLException, ApplicationException {
		log.debug("update debug started");
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		int i = 0;
		FacultyBean Bean = new FacultyBean();
		/// update college name by college id in data base//
		CollegeModel collegeModel = new CollegeModel();
		CollegeBean collegeBean = new CollegeBean();
		collegeBean = collegeModel.findByPK(bean.getCollegeId());
		String collegeName = collegeBean.getName();

		// Update subject Name by subject id in data base//////
		SubjectModel subModel = new SubjectModel();
		SubjectBean subBean = new SubjectBean();
		subBean = subModel.findByPk(bean.getSubjectId());
		String subName = subBean.getSubjectName();

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sql = new StringBuffer(
					"update st_faculty set First_Name=?,Last_Name=?,Login_Id=?,Date_Of_Joining=?,Mobile_No=?,College_Name=?,COllege_Id=?,Subject_Name=?,Subject_Id=?,Created_By=?,Modified_By=?, Created_Datetime=?,Modified_Datetime=? where ID=?");
			pstm = conn.prepareStatement(sql.toString());
			pstm.setString(1, bean.getFirstName());
			pstm.setString(2, bean.getLastName());
			pstm.setString(3, bean.getLoginId());
			pstm.setDate(4, new java.sql.Date(bean.getDoj().getTime()));
			pstm.setString(5, bean.getMobileNo());
			pstm.setString(6, collegeName);
			pstm.setLong(7, bean.getCollegeId());
			pstm.setString(8, subName);
			pstm.setLong(9, bean.getSubjectId());
			pstm.setString(10, bean.getCreatedBy());
			pstm.setString(11, bean.getModifiedBy());
			pstm.setTimestamp(12, bean.getCreatedDatetime());
			pstm.setTimestamp(13, bean.getModifiedDatetime());
			pstm.setLong(14, bean.getId());
			i = pstm.executeUpdate();
			if (i > 0) {
				System.out.println("updated:record " + i);
			} else {
				System.out.println("update unsuccessful");
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
	public List search(FacultyBean bean) throws SQLException {
		return search(bean, 0, 0);
	}

	@SuppressWarnings({ "rawtypes", "deprecation", "unchecked" })
	public List search(FacultyBean Bean, int pageNo, int pageSize) throws SQLException {
		log.debug("search debug started");
		List list = new ArrayList();
		Connection conn = null;
		PreparedStatement pstm = null;

		StringBuffer sql = new StringBuffer("select * from st_faculty where 1=1");
		/////dynamic injection return true value and at run time it will provide attribute to append with the query 
		if (Bean != null) {
			if (Bean.getId() > 0) {
				sql.append(" and ID=" + Bean.getId());
			}
			if (Bean.getFirstName() != null && Bean.getFirstName().length() > 0) {
				// System.out.println("first name of fac in
				// model"+Bean.getFirstName());
				sql.append(" and first_name like '" + Bean.getFirstName() + "%'");
			}
			if (Bean.getLastName() != null && Bean.getLastName().length() > 0) {
				sql.append(" and last_name like '" + Bean.getLastName() + "%'");
			}
			if (Bean.getLoginId() != null && Bean.getLoginId().length() > 0) {
				sql.append(" and login_id like '" + Bean.getLoginId() + "%'");
			}
			if (Bean.getDoj() != null && Bean.getDoj().getDate() > 0) {
				sql.append(" and date_of_joining = '" + Bean.getDoj());
			}
			if (Bean.getMobileNo() != null && Bean.getMobileNo().length() > 0) {
				sql.append(" and mobile_no like '" + Bean.getMobileNo() + "%'");
			}
			if (Bean.getCollegeName() != null && Bean.getCollegeName().length() > 0) {
				sql.append(" and college_name like '" + Bean.getCollegeName() + "%'");
			}
			if (Bean.getCollegeId() > 0) {
				sql.append(" and college_id=" + Bean.getCollegeId());
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

			ResultSet rs = null;
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			pstm = conn.prepareStatement(sql.toString());
			rs = pstm.executeQuery();
			FacultyBean bean = null;
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLoginId(rs.getString(4));
				bean.setDoj(rs.getDate(5));
				bean.setMobileNo(rs.getString(6));
				bean.setCollegeName(rs.getString(7));
				bean.setCollegeId(rs.getLong(8));
				bean.setSubjectName(rs.getString(9));
				bean.setSubjectId(rs.getLong(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDatetime(rs.getTimestamp(13));

				bean.setModifiedDatetime(rs.getTimestamp(14));
				list.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.commit();
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("search debug completed");
		// System.out.println("hi fac model "+list.size());
		return list;

	}

	@SuppressWarnings("rawtypes")
	public List list() throws SQLException {
		return list(0, 0);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List list(int pageNo, int pageSize) throws SQLException {
		Connection conn = null;
		PreparedStatement pstm = null;
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer("select * from st_faculty ");

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append("limit " + pageNo + "," + pageSize);
		}

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			pstm = conn.prepareStatement(sql.toString());
			ResultSet rs = pstm.executeQuery();
			FacultyBean bean = null;
			while (rs.next()) {
				bean = new FacultyBean();
				bean.setId(rs.getLong(1));
				bean.setFirstName(rs.getString(2));
				bean.setLastName(rs.getString(3));
				bean.setLoginId(rs.getString(4));
				bean.setDoj(rs.getDate(5));
				bean.setMobileNo(rs.getString(6));
				bean.setCollegeName(rs.getString(7));
				bean.setCollegeId(rs.getLong(8));
				bean.setSubjectName(rs.getString(9));
				bean.setSubjectId(rs.getLong(10));
				bean.setCreatedBy(rs.getString(11));
				bean.setModifiedBy(rs.getString(12));
				bean.setCreatedDatetime(rs.getTimestamp(13));
				bean.setModifiedDatetime(rs.getTimestamp(14));
				list.add(bean);
			}
		} catch (Exception e) {
			// throw new RecordNotFoundException("exception in list search");
			e.printStackTrace();
		} finally {
			pstm.close();
			conn.commit();
			JDBCDataSource.closeConnection(conn);
		}

		return list;

	}
}
