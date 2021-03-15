package in.co.rays.proj4.test;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.bean.FacultyBean;
import in.co.rays.proj4.model.FacultyModel;

public class FacultyModelTest {

	public static void main(String[] args) throws SQLException, ParseException, DuplicateRecordException, RecordNotFoundException 
	{
		/*
		FacultyModel model=new FacultyModel();
		int i=model.nextPk();
		System.out.println("pk in time faculty model: "+i);*/
		//testAdd();
		//testDelete();
		//testUpdate();
		//testFindByLogin();
		//testFindByPk();
		testSearch();
		//testList();
	}
	
	
	public static void testAdd() throws ParseException, SQLException, DuplicateRecordException, ApplicationException
	{
		SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
		Date date = sdf.parse("01/12/2018");
		long l=0;
		
		FacultyBean bean=new FacultyBean();
		FacultyModel model =new FacultyModel();
		bean.setFirstName("RAKSHA");
		bean.setLastName("UPADHYAY");
		bean.setLoginId("rk@gmail.com");
		bean.setDoj(date);
		bean.setMobileNo("9713827472");
		bean.setCollegeId(101);
		bean.setCollegeName("IET-DAVV");
		bean.setSubjectId(103);
		bean.setSubjectName("VLSI");
		bean.setCreatedBy("root");
		bean.setModifiedBy("root");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
		l=model.add(bean);
		System.out.println("model add"+l);
		
	}
	
	public static void testDelete() throws SQLException, ApplicationException
	{

		FacultyBean bean=new FacultyBean();
		FacultyModel model =new FacultyModel();
		bean.setId(4);
		model.delete(bean);
	}

	public static void testUpdate() throws ParseException, DuplicateRecordException, SQLException, ApplicationException
	{

	    SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
		Date date = sdf.parse("01/12/2018");
		FacultyBean bean=new FacultyBean();
		FacultyModel model =new FacultyModel();
		bean.setId(3);
		bean.setFirstName("priyadarshni");
		bean.setLastName("singh");
		bean.setLoginId("psashok@gmail.com");
		bean.setDoj(date);
		bean.setMobileNo("8770145589");
		bean.setSubjectName("digital communication");
		bean.setCollegeName("SOEX");
		bean.setCreatedBy("root");
		bean.setModifiedBy("root");
		bean.setCreatedDatetime(new Timestamp(new Date().getTime()));
		bean.setModifiedDatetime(new Timestamp(new Date().getTime()));
        model.update(bean);
}
	
	public  static void testFindByLogin() throws SQLException
	{

		FacultyBean bean=new FacultyBean();
		FacultyModel model =new FacultyModel();
		bean=model.findByLoginId("kpkomal27@gmail.com");
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
		System.out.println(bean.getLoginId());
		System.out.println(bean.getDoj());
		
		System.out.println(bean.getMobileNo());
		System.out.println(bean.getCollegeName());
		System.out.println(bean.getSubjectName());
		System.out.println(bean.getId());
		
		
	}
	
	public static void testFindByPk() throws SQLException, RecordNotFoundException
	{
        FacultyBean bean=new FacultyBean();
		FacultyModel model =new FacultyModel();	
		bean=model.findByPk(10);
		System.out.println(bean==null);
		if(bean==null)
		{
			throw new RecordNotFoundException("no record exist...!!!! plz enter a correct id");
		}
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
		System.out.println(bean.getLoginId());
		System.out.println(bean.getMobileNo());
		System.out.println(bean.getCollegeName());
		System.out.println(bean.getSubjectName());
		System.out.println(bean.getCreatedBy());
		System.out.println(bean.getId());
		System.out.println(bean.getModifiedBy());
		System.out.println(bean.getCreatedDatetime());
	}
	
	public static void testSearch() throws ParseException, SQLException
	{
         SimpleDateFormat sdf=new SimpleDateFormat("MM/dd/yyyy");
         Date date = sdf.parse("12/01/2018");
         List list=new ArrayList();
        FacultyBean bean=new FacultyBean();
		FacultyModel model =new FacultyModel();	
		//bean.setFirstName("vaibhav");
		bean.setLastName("neema");
		//bean.setCollegeName("SOEX");
		//bean.setSubjectName("digital communication");
		//bean.setCollegeId(101);
		//bean.setSubjectId(102);
		//bean.setDoj(date);                                              doubt
		//bean.setMobileNo("9713827473");
		//bean.setLoginId("kpkomal27@gmail.com");
		 list=model.search(bean, 1, 2);
		 Iterator it =list.iterator();
		 FacultyBean Bean=null;
		 while(it.hasNext())
		 {
			Bean=(FacultyBean) it.next();
			System.out.println(Bean.getFirstName());
			System.out.println(Bean.getLastName());
			System.out.println(Bean.getLoginId());
			System.out.println(Bean.getCollegeName());
			System.out.println(Bean.getSubjectName());
			System.out.println(Bean.getId());
			System.out.println(Bean.getMobileNo());
			System.out.println(Bean.getCreatedBy());
			System.out.println(Bean.getModifiedBy());
		 }
	}
	
	public static void testList() throws SQLException
	{
		List list =new ArrayList();
		
	FacultyBean bean=new FacultyBean();
	FacultyModel model=new FacultyModel();
	list=model.list(1, 3);
	Iterator it=list.iterator();
	while(it.hasNext())
	{
		bean=(FacultyBean)it.next();
		System.out.println(bean.getFirstName());
		System.out.println(bean.getLastName());
		System.out.println(bean.getLoginId());
		System.out.println(bean.getMobileNo());
		System.out.println(bean.getCollegeName());
		System.out.println(bean.getSubjectName());
		System.out.println(bean.getCollegeId());
		System.out.println(bean.getId());
		System.out.println(bean.getCreatedBy());
	}
	}
}
