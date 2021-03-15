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
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.bean.FacultyBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.model.CollegeModel;
import in.co.rays.proj4.model.FacultyModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * faculty functionality Controller. Performs operation for add, update, delete
 * and get College
 * 
 */
@WebServlet(urlPatterns="/ctl/FacultyCtl")
public class FacultyCtl extends BaseCtl 
{
	private static final long serialVersionUID = 1L;
	private static Logger log=Logger.getLogger(FacultyCtl.class);
	
	
	@Override
	protected void preload(HttpServletRequest request)
	{
//		log.debug("faculty ctl preload debug started");
		SubjectModel subjectModel=new SubjectModel();
		CollegeModel collegeModel=new CollegeModel();
		try
		{
			List subjectList=subjectModel.list();
			request.setAttribute("subjectList", subjectList);
			
			List collegeList = collegeModel.list();
			request.setAttribute("collegeList",collegeList );
			
		}
		catch (Exception e) 
		{
		log.error(e);	
		}
		log.debug("faculty ctl preload debug completed");
	}
	
	
	@Override
	protected boolean validate(HttpServletRequest request)
	{
	log.debug("faculty ctl validate debug started");
	String login = request.getParameter("loginId");
    String doj = request.getParameter("doj");
	
     boolean pass=true;
     
     if(DataValidator.isNull(request.getParameter("firstName")))
     {
    	 
    	 request.setAttribute("firstName", PropertyReader.getValue("error.require", "First Name"));
    	 pass=false;
    	 
     } else if(!DataValidator.isName(request.getParameter("firstName")))
     {
     	request.setAttribute("firstName",PropertyReader.getValue("error.name", "First Name"));
     	pass=false;
     }
     
     if(DataValidator.isNull(request.getParameter("lastName")))
     {
    	 request.setAttribute("lastName", PropertyReader.getValue("error.require", "Last Name"));
         pass=false;
         
     }else if(!DataValidator.isName(request.getParameter("lastName")))
     {
     	request.setAttribute("lastName",PropertyReader.getValue("error.name", "Last Name"));
     	pass=false;
     }
     
     if(DataValidator.isNull(login))
     {
    	request.setAttribute("loginId",PropertyReader.getValue("error.require", "Login Id "));
    	pass=false;
    	
     }
     else if(!DataValidator.isEmail(login))
     {
    	 request.setAttribute("loginId",PropertyReader.getValue("error.email", "LoginID"));
    	 pass=false;
    	 
     }
    
     if(DataValidator.isNull(doj))
     {
    	 request.setAttribute("doj", PropertyReader.getValue("error.require", "Date of joining"));
    	 pass=false;
    	 
     } else if(!DataValidator.isDate(doj))
     {
    	request.setAttribute("doj",PropertyReader.getValue("error.date", "Date of joining"));
    	pass=false;
    	
     }
     
     if(DataValidator.isNull(request.getParameter("mobileNo")))
     {
    	 request.setAttribute("mobileNo", PropertyReader.getValue("error.require", "Mobile no"));
    	 pass=false;
    	 
     }else if(!DataValidator.isMobileNo(request.getParameter("mobileNo")))
     {
    	 request.setAttribute("mobileNo", PropertyReader.getValue("error.mobile", " "));
    	 pass=false;
     }
     
     if(DataValidator.isNull(request.getParameter("collegeId")))
     {
    	 request.setAttribute("collegeId", PropertyReader.getValue("error.require", "College Name"));
    	 pass=false;
     }

     if(DataValidator.isNull(request.getParameter("subjectId")))
     {
    	 request.setAttribute("subjectId", PropertyReader.getValue("error.require", "Subject Name"));
    	 pass=false;
     }
     
     
     log.debug("faculty ctl validate debug completed");
	return pass;	
	}
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request)
	{
    	
	log.debug("faculty ctl populate bean debug started");
	
	FacultyBean bean=new FacultyBean();
	
	bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
	
	bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
	
	bean.setId(DataUtility.getLong(request.getParameter("id")));
	
	bean.setLoginId(DataUtility.getString(request.getParameter("loginId")));
	
	bean.setDoj(DataUtility.getDate(request.getParameter("doj")));
	
	bean.setMobileNo(DataUtility.getString(request.getParameter("mobileNo")));

	
	bean.setCollegeId(DataUtility.getLong(request.getParameter("collegeId")));
	
	bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
	
	populateDTO(bean, request);
	
	log.debug("faculty ctl populate bean debug completed");
	
	return bean;
	}

    /**
     * Contains display logic
     */
	@Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
    	{
    		log.debug("faculty ctl doget debug started");
    		
    		String op = DataUtility.getString(request.getParameter("operation"));
    		FacultyModel model=new FacultyModel();
    		long id= DataUtility.getLong(request.getParameter("id"));
    		
    		if(id>0)
    		{
    		FacultyBean bean=new FacultyBean();
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
    		
    		log.debug("faculty ctl doget debug completed");
    	}

    /**
     * Contains submit logic
     */
    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
    {
    	log.debug("faculty ctl doget debug started");
    	String op=DataUtility.getString(request.getParameter("operation"));
    	FacultyModel facmodel = new FacultyModel();
    	long id=DataUtility.getLong(request.getParameter("id"));
    	
    	if(OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op))
    	{
    		FacultyBean bean=(FacultyBean)populateBean(request);
      		 try{
       			 if(id>0)
       			 {
       				
       				try {
       					facmodel.update(bean);
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
       	                
       				}  
       			 }
       			 else
       			 {
       				 long pk=0;
       			    try {
       					pk=facmodel.add(bean);
       					
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
    		FacultyBean bean=(FacultyBean)populateBean(request);
    		try {
				facmodel.delete(bean);
				ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
				return;
			} catch (Exception e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
    		
    	}
    	else if(OP_RESET.equalsIgnoreCase(op))
    	{
    	ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
    	return;
    	}
    	else if(OP_CANCEL.equalsIgnoreCase(op))
    	{
    	ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);	
    	return;
    	}
    	log.debug("faculty ctl doget debug completed");
    }


	@Override
	protected String getView() {
		
		return ORSView.FACULTY_VIEW;
	}

}
