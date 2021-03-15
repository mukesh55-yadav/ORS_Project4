package in.co.rays.proj4.test;


import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.bean.TimeTableBean;
import in.co.rays.proj4.model.TimeTableModel;

public class TimeTableModelTest 
{
	
	public static void main(String[] args) throws Exception 
	{
		////////////testPk/////////////////////////
		/*
		TimeTableModel tm=new TimeTableModel();
		int i=tm.nextPk();
		System.out.println("pk in time table model "+i);*/
		
		////////////////**********/////////////////
		
		 //testAdd();
		//testDelete();
		//testUpdate();
		//testFindBySubNameCouName();
		//testFindcouNameExDate();
		//testCouNameSubNameExDateExTime();
		// testFindByPk();
		//testSearch();
		testList();
	}
	
	
	public static void testAdd() throws ParseException
	{
		TimeTableModel ttm=new TimeTableModel();
		TimeTableBean bean=new TimeTableBean();
		 long i=0;
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
		java.util.Date date = sdf.parse("05/11/2018");

		try 
		{
	     bean.setCourseName("MCA");
	     bean.setCourseId(104);
	     bean.setSubjectName("computer application");
	     bean.setSubjectId(103);
	     bean.setExamDate(date);
	     bean.setExamTime("12:00");     //doubt
	     bean.setSemester(1);
	     bean.setCreatedBy("self");
	     bean.setModifiedBy("self");
	     bean.setCreatedDatetime(new Timestamp(new java.util.Date().getTime()));
	     bean.setModifiedDatetime(new Timestamp(new java.util.Date().getTime()));
	   i= ttm.add(bean);
        } 
		catch (Exception e) 
		{
		e.printStackTrace();	
		}
		System.out.println(i+":record added");
	}
	 
	public static void testDelete() throws Exception
	{
		TimeTableBean bean=new TimeTableBean();
		TimeTableModel model = new TimeTableModel();
		bean.setId(4);
		model.delete(bean);
		System.out.println("delete completed");
	}
	
	public static void testUpdate() throws ParseException, SQLException, DuplicateRecordException, RecordNotFoundException
	{
         SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
         Date date =new Date();
         Date d1=sdf.parse("08/05/2018");
		 TimeTableBean bean=new TimeTableBean();
		TimeTableModel model = new TimeTableModel();
		bean.setId(4);
		bean.setCourseId(102);
		bean.setCourseName("B-Tech");
		bean.setSubjectId(103);
		bean.setSubjectName("Automobiles");
		bean.setExamDate(d1);
		bean.setExamTime("09:00");
		bean.setCreatedBy("root");
		bean.setModifiedBy("root");
		bean.setSemester(4);
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		
		model.update(bean);
		System.out.println("updated");
	}
	
	public static void testFindBySubNameCouName() throws SQLException, RecordNotFoundException
	{

		TimeTableBean bean=new TimeTableBean();
		TimeTableModel model = new TimeTableModel();
		bean=model.findByCourseNameSubjectName("MBA", "ECONOMICS");
		if(bean==null)
		{
			System.out.println("no such record found");
		}
		else
		{

			System.out.println(bean.getCourseId());
			System.out.println(bean.getCourseName());
			System.out.println(bean.getSubjectName());
			System.out.println(bean.getSubjectId());
			System.out.println(bean.getId());
			System.out.println(bean.getExamDate());
			System.out.println(bean.getExamTime());
			System.out.println(bean.getCreatedBy());
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime());	
		}
	}
	
	public static void testFindcouNameExDate() throws ParseException, RecordNotFoundException, SQLException 
	{
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date date=new Date();
        date=sdf.parse("05/01/1990");
        
		TimeTableBean bean=new TimeTableBean();
		TimeTableModel model = new TimeTableModel();
		 bean =model.findByCourseNameExamDate("MB", date);
		 System.out.println(bean==null);
		 if(bean==null)
		 {
		 throw new RecordNotFoundException("such record not found in database");
		 
		 }
		 else
		 {
			System.out.println(bean.getId()); 
			System.out.println(bean.getCourseId()); 
			System.out.println(bean.getCourseName()); 
			System.out.println(bean.getSubjectId()); 
			System.out.println(bean.getSubjectName()); 
			System.out.println(bean.getExamDate()); 
			System.out.println(bean.getExamTime()); 
			System.out.println(bean.getSemester()); 
			System.out.println(bean.getCreatedDatetime()); 
			System.out.println(bean.getCreatedBy()); 
			System.out.println(bean.getModifiedBy());
			System.out.println(bean.getModifiedDatetime()); 
		 }
	}
	
	public static void testCouNameSubNameExDateExTime() throws ParseException, SQLException, RecordNotFoundException
	{
     SimpleDateFormat sdf= new  SimpleDateFormat("MM/dd/yyyy");
      Date date = new Date();
      date = sdf.parse("05/01/1990");
	 TimeTableBean bean=new TimeTableBean();
	 TimeTableModel model=new TimeTableModel();
	bean= model.findByCourseNameSubjectNameExamDateExamTime("MBA", "ECONOMICS", date, "15:00");
	//System.out.println(bean==null);
	if(bean==null)
	{
	throw new RecordNotFoundException("no such record found");
    }
	else 
	{
		System.out.println(bean.getCourseId());
		System.out.println(bean.getCourseName());
		System.out.println(bean.getSubjectName());
		System.out.println(bean.getSubjectId());
        System.out.println(bean.getExamDate());
        System.out.println(bean.getExamTime());
        System.out.println(bean.getId());
        System.out.println(bean.getCreatedBy());
      }
	}
	
	public static void testFindByPk() throws SQLException
	{

		TimeTableBean bean=new TimeTableBean();
		TimeTableModel model = new TimeTableModel();
		bean=model.findByPk(2);
		System.out.println(bean.getCourseId());
		System.out.println(bean.getCourseName());
		System.out.println(bean.getSubjectId());
		System.out.println(bean.getSubjectName());
		System.out.println(bean.getExamDate());
		System.out.println(bean.getExamTime());
		System.out.println(bean.getSemester());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedDatetime());
		System.out.println(bean.getId());
		
		System.out.println("find by completed");
		
	}
	
	public static void testSearch() throws ParseException, SQLException
	{
         SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
         Date date=sdf.parse("09/27/2018");
         List list=new ArrayList();
		TimeTableBean bean=new TimeTableBean();
		TimeTableModel model = new TimeTableModel();
		//bean.setCourseId(102);
		//bean.setCourseName("BCA");
		//bean.setSubjectId(103);
		//bean.setSubjectName("Economics");
		//bean.setExamDate(date);             //doubt  
		 // bean.setId(2);
		  list =model.search(bean, 1, 1);
		  Iterator it = list.iterator();
		  TimeTableBean Bean=null;
		  while(it.hasNext())
		  {
			 Bean= (TimeTableBean)it.next();
			 System.out.println(Bean.getCourseId());
			 System.out.println(Bean.getCourseName());
			 System.out.println(Bean.getSubjectId());
			 System.out.println(Bean.getSubjectName());
			 System.out.println(Bean.getExamTime());
			 System.out.println(Bean.getExamDate());
			 System.out.println(Bean.getSemester());
			 System.out.println(Bean.getCreatedBy());
			 
		  }
		  System.out.println("test search completed");
	}
	
	public static void testList() throws SQLException
	{
		List list = new ArrayList();
		TimeTableBean bean=new TimeTableBean();
		TimeTableModel model = new TimeTableModel();
		list=model.list( 1,4);
		Iterator it = list.iterator();
		while(it.hasNext())
		{
		bean=(TimeTableBean)it.next();
		System.out.println(bean.getCourseId());
		System.out.println(bean.getCourseName());
		System.out.println(bean.getSubjectId());
		System.out.println(bean.getSubjectName());
		System.out.println(bean.getExamTime());
		System.out.println(bean.getExamDate());
		System.out.println(bean.getId());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
		System.out.println(bean.getModifiedDatetime());
		
		
		}
	}

}
