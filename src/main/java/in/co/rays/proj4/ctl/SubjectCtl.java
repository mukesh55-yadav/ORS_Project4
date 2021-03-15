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
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * subject functionality Controller. Performs operation for add, update, delete
 * and get subject
 * 
 */
@WebServlet(urlPatterns="/ctl/SubjectCtl")
public class SubjectCtl extends BaseCtl 
{
	private static final long serialVersionUID = 1L;
	private static Logger log=Logger.getLogger(SubjectCtl.class);
	
	   @Override
	   protected void preload(HttpServletRequest request)
	   {
		   List list=new ArrayList();
		   CourseModel model=new CourseModel();
		   System.out.println("hi preload of subject ctl ");
		  try 
		  {
			  list= model.list();
			  request.setAttribute("courseList",list);
			  
		  } 
		  catch (Exception e) 
		  {
			  log.error(e);
			  e.printStackTrace();
		  }
	   }
	
	@Override
	protected boolean validate(HttpServletRequest request)
	{ 
		log.debug("subjectctl valiadte debug started");
        boolean pass = true;
        //System.out.println("im in validate of userctl");
        

        if (DataValidator.isNull(request.getParameter("subjectName"))) {
            request.setAttribute("subjectName",PropertyReader.getValue("error.require", "Subject Name"));
            pass = false;
        } else if (!DataValidator.isNameNumber(request.getParameter("subjectName"))) {
            request.setAttribute("subjectName",
                    PropertyReader.getValue("error.pass", "Subject Name"));
            pass = false;
        }

         System.out.println("subject ctl validate"+request.getParameter("courseId"));
         
        if (DataValidator.isNull(request.getParameter("courseId"))) {
            request.setAttribute("courseId",PropertyReader.getValue("error.require", "Course Name"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("description"))) {
            request.setAttribute("description",PropertyReader.getValue("error.require", "Description"));
            pass = false;
        }
      log.debug("subjectctl validate debug completed");
		return pass;
	}
	
	@Override
    protected BaseBean populateBean(HttpServletRequest request)
    {
		log.debug("subject ctl populate bean debug started");
		
		SubjectBean bean=new SubjectBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		
	    bean.setSubjectName(DataUtility.getString(request.getParameter("subjectName")));
		
        bean.setDescription(DataUtility.getString(request.getParameter("description")));
		
		populateDTO(bean, request);
		
		log.debug("subject ctl populate bean debug completed");
		
		return bean;
    }

    /**
     * Contains Display logics
     */
	@Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
    log.debug("subject ctl doget started");
    String op=DataUtility.getString(request.getParameter("operation"));
    SubjectBean bean=null;
    SubjectModel model=new SubjectModel();
    long id=DataUtility.getLong(request.getParameter("id"));
	
    if(id>0)
    {
     try 
     {
    	bean= model.findByPk(id);
    	
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
    log.debug("subject ctl doget completed");
   }

    /**
     * Contains submit logics
     */
	@Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		log.debug("subject ctl dopost debug started");
		String op = DataUtility.getString(request.getParameter("operation"));
        // get model
		
        SubjectModel submodel = new SubjectModel();
        long id = DataUtility.getLong(request.getParameter("id"));
        
        if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) 
        {
        	SubjectBean bean = (SubjectBean) populateBean(request);
   		 try{
   			 if(id>0)
   			 {
   				
   				try {
   					submodel.update(bean);
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
   					pk=submodel.add(bean);
   					
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
		else if(OP_CANCEL.equalsIgnoreCase(op))
		{
		ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
		return;
		}
		else if(OP_RESET.equalsIgnoreCase(op))
		{
		ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);
		return;
		}
		ServletUtility.forward(getView(), request, response);
		log.debug("subject ctl dopost debug completed");
	}
	@Override
	protected String getView() 
	{
		return ORSView.SUBJECT_VIEW;
	}

}
