package in.co.rays.proj4.ctl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.bean.TimeTableBean;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.model.TimeTableModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * timetable  functionality Controller. Performs operation for add, update, delete
 * and get time table
 * 
 */
@WebServlet(urlPatterns="/ctl/TimeTableCtl")
public class TimeTableCtl extends BaseCtl 
{
	 private static final long serialVersionUID = 1L;

	 private static Logger log = Logger.getLogger(TimeTableCtl.class);
	
	 
	 @Override
	 protected void preload(HttpServletRequest request)
	 {
		log.debug("time table ctl debug started"); 
		List courseList=null;
		List subjectList=null;
		CourseModel courseModel=new CourseModel();
		SubjectModel subjectModel=new SubjectModel();
		try 
		{
		courseList=courseModel.list();	
		subjectList=subjectModel.list();
		
		request.setAttribute("courseList",courseList);
		request.setAttribute("subjectList",subjectList);
		
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	log.debug("time table ctl debug completed"); 
	 }
	 
	 @Override
	 protected boolean validate(HttpServletRequest request) 
     {
		 log.debug("time table ctl validate debug started");
		 boolean pass=true;
		 
		 if(DataValidator.isNull(request.getParameter("courseId")))
		 {
			 request.setAttribute("courseId", PropertyReader.getValue("error.require", "Course Name"));
			
			 pass=false;
		 }
		 
	
		 
		 if(DataValidator.isNull(request.getParameter("subjectId")))
		 {
			request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject name")); 
			
			pass=false;
		 }
		 
		 if(DataValidator.isNull(request.getParameter("examTime")))
		 {
			 request.setAttribute("examTime", PropertyReader.getValue("error.require", "Exam Time"));
				
			 pass=false;
		 }
		 
		 if(DataValidator.isNull(request.getParameter("examDate")))
		 {
			request.setAttribute("examDate", PropertyReader.getValue("error.require", "Exam Date"));
		
			pass=false;
		 } 
		 
		 log.debug("time table ctl validate debug completed");
		 
		return pass; 
	 }
	 
	 
	 @Override
	 protected BaseBean populateBean(HttpServletRequest request)
	 {
		 log.debug("time table ctl populatebean debug completed");
		 
		 TimeTableBean bean=new TimeTableBean();
		 
		 bean.setId(DataUtility.getLong(request.getParameter("id")));
		 
		 bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		 
	     bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
		 
		 bean.setExamDate(DataUtility.getDate(request.getParameter("examDate")));
		
		 bean.setExamTime(DataUtility.getString(request.getParameter("examTime")));
		
		 populateDTO(bean, request);
		 
		 log.debug("time table ctl populatebean debug completed");
		 
		 return bean;
	 }

	    /**
	     * Contains Display logics
	     */
 @Override
  protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
 {
	 log.debug("time table ctl doget debug completed");
	 
	 String op=DataUtility.getString(request.getParameter("operation"));
	 System.out.println("hi timetable doget"+op);
	 
	 TimeTableModel model=new TimeTableModel();
	 
	 long id=DataUtility.getLong(request.getParameter("id"));
	 System.out.println("hi timetable doget"+id);
	if(id>0)
	{
		TimeTableBean bean=null;
		try {
			bean=model.findByPk(id);
			//System.out.println("id in time table do get"+bean.getId());
			ServletUtility.setBean(bean, request);
		
		
		} 
		catch (Exception e) 
		{

            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
		}
	}
	ServletUtility.forward(getView(), request, response);
	log.debug("time table ctl doget debug completed");
 }

 /**
  * Contains submit logics
  */
 @Override
 protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
 {
	 log.debug("time table ctl dopost debug completed");
	 
	 String op=DataUtility.getString(request.getParameter("operation"));
	 
	 System.out.println("hi dopost in timetable ctl"+op);
	 
	 TimeTableModel model=new TimeTableModel();
	 
	 long id=DataUtility.getLong(request.getParameter("id"));
	 
	 System.out.println("hi dopost in timetable ctl"+id);
	 
	 if(OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op))
	 {
		 
		 TimeTableBean bean=(TimeTableBean)populateBean(request);
		 try{
		 if(id>0)
		 {
			
			try {
				model.update(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Updated Successfully...!!!!", request);
				ServletUtility.forward(getView(),request, response);
				
			} catch (SQLException e) 
			{
				e.printStackTrace();
			} 
			catch (DuplicateRecordException e)
			{
				ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Records already exists", request);
                ServletUtility.forward(getView(),request, response);
                
			} catch (RecordNotFoundException e) {
				ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Records Not Found", request);
                ServletUtility.forward(getView(),request, response);
                e.printStackTrace();
			} 
		 }
		 else
		 {
			 long pk=0;
		    try {
				pk=model.add(bean);
				
				ServletUtility.setSuccessMessage("Saved Successfully", request);
				ServletUtility.forward(getView(), request, response);
				
			} 
		    catch (DuplicateRecordException e) 
		    {
		    	ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Records already exists", request);
				e.printStackTrace();
			} 
		    catch (SQLException e) 
		    {
				
				e.printStackTrace();
			} 
		    catch (RecordNotFoundException e) 
		    {
		    	ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage("Records Not Found", request);
				e.printStackTrace();
			}

            bean.setId(pk);
            ServletUtility.setErrorMessage("Record already exist....!!!!", request);
            ServletUtility.setBean(bean, request);
            ServletUtility.forward(getView(), request, response);
            
		   
		 }
		 }
		 catch(Exception e)
		 {
			 ServletUtility.setBean(bean, request);
			 ServletUtility.setErrorMessage("record already exist", request);
			 e.printStackTrace();
		 }
		
		 
	 } 
	 else if(OP_DELETE.equalsIgnoreCase(op))
	 {
		 TimeTableBean bean = (TimeTableBean) populateBean(request);
         try 
         {
             model.delete(bean);
             ServletUtility.setSuccessMessage("Record Deleted Successfully...!!!", request);
             ServletUtility.redirect(ORSView.TIME_TABLE_LIST_CTL, request,response);
             return;
          } 
         catch (Exception e) 
         {
             log.error(e);
             ServletUtility.handleException(e, request, response);
             return;
         }

	 }
	 else if(OP_CANCEL.equalsIgnoreCase(op))
	 {
	  ServletUtility.redirect(ORSView.TIME_TABLE_LIST_CTL, request, response);
	  return;
	 }
	 else if(OP_RESET.equalsIgnoreCase(op))
	 {
	  ServletUtility.redirect(ORSView.TIME_TABLE_CTL, request, response);
	  return;
	 }
	 
	 
	 log.debug("time table ctl dopost debug completed");
 
 }
	 
	@Override
	protected String getView() {
		
		return ORSView.TIME_TABLE_VIEW;
	}

}
