package in.co.rays.proj4.test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.model.SubjectModel;

public class SubjectModelTest {

	public static void main(String[] args) throws SQLException 
	{/*
		      SubjectBean bean=new SubjectBean();
		       SubjectModel model=new SubjectModel();
		      int i=model.nextPk();
		      System.out.println("upcoming id from next pk"+i);*/
		      //testAdd();
		     //testDelete();
		     //testUpdate();
		      // testFindBySubjectName();
		       //testFindByPk();
		        //testSearch();
		         testList();
}
	
	public static void testAdd() throws SQLException, DuplicateRecordException, RecordNotFoundException
	{

	        SubjectBean bean=new SubjectBean();
	        SubjectModel model=new SubjectModel();
	        bean.setCourseName("MBA");
	        bean.setCourseId(104);
	        bean.setSubjectName("humane science");
	        bean.setSubjectId(104);
	        bean.setDescription("masters in mangment");
	        bean.setCreatedBy("root");
	        bean.setModifiedBy("root");
	        bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
	        bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
	        model.add(bean);
	}
	public static void testDelete() throws SQLException, ApplicationException
	{

        SubjectBean bean=new SubjectBean();
        SubjectModel model=new SubjectModel();
        bean.setId(1);
        model.delete(bean);
	}
	
	public static void testUpdate() throws SQLException, DuplicateRecordException, RecordNotFoundException
	{

        SubjectBean bean=new SubjectBean();
        SubjectModel model=new SubjectModel();
        bean.setModifiedBy("self");
        bean.setCreatedBy("self");
        bean.setId(1);
        bean.setCourseId(108);
        bean.setSubjectName("economic");
        bean.setCourseName("CA");
        model.update(bean);
	}
	
	public static void testFindBySubjectName() throws SQLException
	{

        SubjectBean bean=new SubjectBean();
        SubjectModel model=new SubjectModel();
        bean= model.findBySubjectName("economic");
       System.out.println(bean.getId());
       System.out.println(bean.getCourseId());
       System.out.println(bean.getCourseName());
       System.out.println( bean.getSubjectId());
       System.out.println(bean.getSubjectName());
       System.out.println(bean.getDescription());
       System.out.println( bean.getCreatedBy());
       System.out.println(bean.getCreatedDatetime());
       System.out.println(bean.getModifiedBy());
       System.out.println(bean.getModifiedDatetime());
	}
	
	public static void testFindByPk() throws SQLException
	{

        SubjectBean bean=new SubjectBean();
        SubjectModel model=new SubjectModel();
        bean=model.findByPk(2);
        System.out.println(bean.getId());
        System.out.println(bean.getCourseId());
        System.out.println(bean.getCourseName());
        System.out.println( bean.getSubjectId());
        System.out.println(bean.getSubjectName());
        System.out.println(bean.getDescription());
        System.out.println( bean.getCreatedBy());
        System.out.println(bean.getCreatedDatetime());
        System.out.println(bean.getModifiedBy());
        System.out.println(bean.getModifiedDatetime());
	}
	
	public static void testSearch() throws SQLException
	{

        SubjectBean bean=new SubjectBean();
        SubjectModel model=new SubjectModel();
        List list=new ArrayList();
       //bean.setCourseId(103);
        //bean.setCourseName("BA");
        //bean.setSubjectName("economic");
       // bean.setSubjectId(104);
         //  bean.setId(1);
          list = model.search(bean, 1, 4);
          Iterator it =list.iterator();
          while(it.hasNext())
          {
        	SubjectBean Bean=(SubjectBean)it.next();
            System.out.print(" "+Bean.getCourseId());
        	System.out.print(" "+Bean.getCourseName());
        	System.out.print(" "+Bean.getSubjectId());
        	System.out.print(" "+Bean.getSubjectName());
        	System.out.print(" "+Bean.getDescription());
        	System.out.print(" "+Bean.getId());
        	System.out.println(" "+Bean.getCreatedBy());
          }
	}
	
	public static void testList() throws SQLException
	{
         List list=new ArrayList();
        SubjectBean bean=new SubjectBean();
        SubjectModel model=new SubjectModel();
        list=model.list(1,0);
        Iterator it=list.iterator();
        while(it.hasNext())
        {
           bean=(SubjectBean)it.next();
           System.out.print(" "+bean.getCourseId());
           System.out.print(" "+bean.getCourseName());
           System.out.print(" "+bean.getSubjectId());
           System.out.print(" "+bean.getSubjectName());
           System.out.print(" "+bean.getDescription());
           System.out.print(" "+bean.getId());
           System.out.print(" "+bean.getCreatedBy());
           System.out.print(" "+bean.getCreatedDatetime());
           System.out.print(" "+bean.getModifiedBy());
           System.out.println(" "+bean.getModifiedDatetime());
        }
       }
}
