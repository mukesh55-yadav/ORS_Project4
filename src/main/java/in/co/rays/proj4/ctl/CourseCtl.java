package in.co.rays.proj4.ctl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * course functionality Controller. Performs operation for add, update, delete
 * and get College
 * 
 */
@WebServlet(name="CourseCtl",urlPatterns="/ctl/CourseCtl")
public class CourseCtl extends BaseCtl
{
	 private static final long serialVersionUID = 1L;
	 
	 private static Logger log= Logger.getLogger(CourseCtl.class);
	
	 
	 @Override
	 protected boolean validate(HttpServletRequest request)
	 {
		 log.debug("Course ctl debug started in validate");
		 boolean pass=true;
		 if(DataValidator.isNull(request.getParameter("courseName")))
		 {
			 request.setAttribute("courseName", PropertyReader.getValue("error.require","Course Name"));
			 pass=false;
		 }else if (!DataValidator.isNameNumber(request.getParameter("courseName"))) {
	            request.setAttribute("courseName",
	                    PropertyReader.getValue("error.pass", "Course Name"));
	            pass = false;
	        }
		 
		 if(DataValidator.isNull(request.getParameter("description")))
		 {		 
			 request.setAttribute("description", PropertyReader.getValue("error.require","Description"));
			 pass=false;
		 }
      log.debug("course ctl debug ended in validate");
	  return pass;	 
	 }
	 
	 @Override
	 protected BaseBean populateBean(HttpServletRequest request)
	 {
		log.debug("populate bean debug started"); 
		CourseBean bean=new CourseBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		
		bean.setCourseName(DataUtility.getString(request.getParameter("courseName")));
		
		bean.setDescription(DataUtility.getString(request.getParameter("description")));
		
		populateDTO(bean, request);
		
		log.debug("populate bean debug completed");
		
	 return bean;
	 }

	    /**
	     * Contains display logic
	     */
	 protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	 {
		log.debug("doget debug started");
		
		String op=DataUtility.getString(request.getParameter("operation"));
		
		CourseModel model=new CourseModel();
		
		long id=DataUtility.getLong(request.getParameter("id"));
		
		if(id>0)
		{
			CourseBean bean;
			
			try 
			{
			bean=model.findByPk(id);
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
		log.debug("doget debug completed");
	 }

	    /**
	     * Contains submit logic
	     */
	 protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
	 {
		 log.debug("dopost debug started");
		 
		   String op=DataUtility.getString(request.getParameter("operation"));
		   CourseModel model=new CourseModel();
		 
		   long id= DataUtility.getLong(request.getParameter("id"));
		   if(OP_SAVE.equalsIgnoreCase(op)|| OP_UPDATE.equalsIgnoreCase(op))
		  {
			CourseBean bean = (CourseBean)populateBean(request);
			 if(id>0)
             {
              try {
			    model.upadte(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data Successfully Updated....!!!", request);
			} 
              catch (SQLException e) 
              {
            	  e.printStackTrace();
              }	 
            }
             else
             {
            	long pk=0; 
            	try {
					pk=model.add(bean);
					ServletUtility.setSuccessMessage("Data Successfully Saved...!!!", request);
					ServletUtility.forward(getView(), request, response);
					return;
					
				} 
            	catch (SQLException e) 
            	{
				e.printStackTrace();
				} 
            	catch (DuplicateRecordException e) 
            	{
				e.printStackTrace();
                 }
            	bean.setId(pk);
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Course name already exist try another one.....!!!!", request);
             }
            
        
			
		  }
		  else if(OP_DELETE.equalsIgnoreCase(op))
		  {
			  CourseBean bean=(CourseBean)populateBean(request);
			  try 
			  {
				model.delete(bean);
				ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
				return;
			   } 
			  catch (Exception e) 
			  {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			  }
			  
		  }
		  else if(OP_RESET.equalsIgnoreCase(op))
		  {
			ServletUtility.redirect(ORSView.COURSE_CTL, request, response);  
			return;
		  }
		  else if(OP_CANCEL.equalsIgnoreCase(op))
		  {
			ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
			return;
		  }
		 ServletUtility.forward(getView(), request, response);
		 
		 log.debug("dopost debug completed");
		 
	 }
	

	@Override
	protected String getView() 
	{
	return ORSView.COURSE_VIEW;
	}

}
