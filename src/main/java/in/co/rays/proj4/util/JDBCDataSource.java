package in.co.rays.proj4.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import com.mchange.v2.c3p0.ComboPooledDataSource;

import in.co.rays.proj4.exception.ApplicationException;

/**
 * JDBC DataSource is a Data Connection Pool
 * 
 * @author Mukesh_Yadav
 * @version 1.0
 * @Copyright (c) SunilOS
 * 
 */

public final class JDBCDataSource {

	
	/**
	 * JDBC Datasource static object
	 */
	private static JDBCDataSource jds = null;

	
	/**
	 * C3P0 database connection pool
	 */
	private ComboPooledDataSource ds = null;

	
	/**
	 * Make default constructor private
	 */
	private JDBCDataSource() {
	try {	
		// Load Resource Bundle File
		ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.proj4.bundle.system");
		// Create data source
		ds = new ComboPooledDataSource();
		// Set DS Properties
		ds.setDriverClass(rb.getString("driver"));
		ds.setJdbcUrl(rb.getString("url"));
		ds.setUser(rb.getString("username"));
		ds.setPassword(rb.getString("password"));
		// Minimum number of connections
		ds.setInitialPoolSize(new Integer((String) rb.getString("initialPoolSize"))); 		
		// Increase connection when existing users are increased
		ds.setAcquireIncrement(new Integer((String) rb.getString("acquireIncrement")));		
		// Maximum number of connections
		ds.setMaxPoolSize(new Integer((String) rb.getString("maxPoolSize")));
		// Time out time MaxIdealTime
		ds.setMaxIdleTime(DataUtility.getInt(rb.getString("timeout")));					
		// Minimum number of Connection
		ds.setMinPoolSize(new Integer((String) rb.getString("minPoolSize")));		
		} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
	}

	
	/**
	 * 
	 * Create instance of Connection Pool
	 * 
	 * @return
	 */
	
	public static JDBCDataSource getInstance() {
		if (jds == null) {
			jds = new JDBCDataSource();
		}
		return jds;
	}

    
	/**
	 * 
	 * Gets the connection from ComboPooledDataSource
	 * 
	 * @return
	 */
	
	public static Connection getConnection() {
		try {
			return getInstance().ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
    
	
	/**
	 * 
	 * Closes a connection
	 * 
	 * @param conn
	 * @param stmt
	 * @param rs
	 */
	
	public static void closeConnection(Connection conn, Statement stmt,ResultSet rs) {
		try {
			if (rs != null)
				rs.close(); // memory will be cleaned
			if (stmt != null)
				stmt.close(); // Cursor will be closed
			if (conn != null)
				conn.close(); // Conn will be returned to pool
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Closes a connection
	 * 
	 * @param conn
	 * @param stmt
	 */
	public static void closeConnection(Connection conn, Statement stmt) {
		closeConnection(conn, stmt, null);
	}
	
	/**
	 * 
	 *  Closes a connection
	 * 
	 * @param conn
	 */
	
	public static void closeConnection(Connection conn) {
		closeConnection(conn, null, null);
	}
    
	
    /**
     * 
     * trnRollBack Connection
     * 
     * @param connection
     * @throws ApplicationException
     */
    public static void trnRollback(Connection connection)
            throws ApplicationException {
        if (connection != null) {
            try {
                connection.rollback();
            } catch (Exception ex) {
                throw new ApplicationException(ex.toString());
            }
        }
    }
}
