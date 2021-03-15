package in.co.rays.proj4.test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.model.CourseModel;

public class CourseModelTest {

	public static void main(String[] args) throws SQLException, DuplicateRecordException 
	{
	/*long i=CourseModel.nextPk();
	System.out.println("id in database"+i);	
	 */
		
		//testAdd();
		//testUpdate();
		//testDelete();
		//testFindByCourseName();
		//testFindByPk();
		//testSearch();
		//testList();
	}
	
	public static void testAdd() throws SQLException, DuplicateRecordException
	{
		CourseModel model = new CourseModel();
		CourseBean bean=new CourseBean();
		long i=0;
		bean.setCourseName("FASHION DESIGNING");
		bean.setDescription("provides specialization in FASHION DESIGN");
		bean.setCreatedBy("root");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedBy("root");
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		i=model.add(bean);
		System.out.println("record added"+i);
	}
	
	public static void testUpdate() throws SQLException
	{

		CourseModel model = new CourseModel();
		CourseBean bean=new CourseBean();
	    bean.setId(4);
		bean.setCourseName("FASHION DESIGNING");
		bean.setDescription("provides specialization in FASHION DESIGN");
		bean.setCreatedBy("root");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedBy("root");
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
	    model.upadte(bean);
		System.out.println("update completed");
	}
	public static void testDelete() throws SQLException, ApplicationException
	{

		CourseModel model = new CourseModel();
		CourseBean bean=new CourseBean();
		bean.setId(4);
		model.delete(bean);
		
	}
	
	public static void testFindByCourseName() throws SQLException
	{

		CourseModel model = new CourseModel();
		CourseBean bean=new CourseBean();
		//bean.setCourseName("mba");
		bean=model.findByCourseName("MSC");
		System.out.println(bean.getCourseName());
		System.out.println(bean.getId());
		System.out.println(bean.getCreatedDatetime());
	    System.out.println(bean.getDescription());
		
		
	}

	public static void testFindByPk() throws SQLException
	{

		CourseModel model = new CourseModel();
		CourseBean bean=new CourseBean();
		bean=model.findByPk(6);
		System.out.println(bean!=null);
		System.out.println(bean.getCourseName());
		System.out.println(bean.getId());
		System.out.println(bean.getDescription());
	}
	
	public static void testSearch() throws SQLException
	{
        List list =new ArrayList();
        CourseBean Bean=null;
		CourseModel model = new CourseModel();
		CourseBean bean=new CourseBean();
		//bean.setId(6);
		bean.setCourseName("test");
		list =model.search(bean, 1, 1);
		Iterator it = list.iterator();
		while(it.hasNext())
		{
		     Bean=(CourseBean) it.next();
		     System.out.println(Bean.getCourseName());
		     System.out.println(Bean.getId());
		     System.out.println(Bean.getDescription());
		     System.out.println(Bean.getCreatedBy());
		     System.out.println(Bean.getModifiedBy());
		}
		System.out.println("search completed");
	}
	

	public static void testList() throws SQLException
	{
        List list =new ArrayList();
        CourseBean Bean=null;
		CourseModel model = new CourseModel();
		CourseBean bean=new CourseBean();
		//bean.setId(6);
		bean.setCourseName("fashion technology");
		list =model.list(1, 2);
		Iterator it = list.iterator();
		while(it.hasNext())
		{
		     Bean=(CourseBean) it.next();
		     System.out.println(Bean.getCourseName());
		     System.out.println(Bean.getId());
		     System.out.println(Bean.getDescription());
		     System.out.println(Bean.getCreatedBy());
		     System.out.println(Bean.getModifiedBy());
		}
		System.out.println(" list completed");
	}
}
