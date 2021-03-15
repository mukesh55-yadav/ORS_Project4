package in.co.rays.proj4.ctl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * subject List functionality Controller. Performs operation for list, search
 * and delete operations of subject
 * 
 */
@WebServlet(urlPatterns="/ctl/SubjectListCtl")
public class SubjectListCtl extends BaseCtl

{
	
	private static Logger log=Logger.getLogger(SubjectListCtl.class);
	
	@Override
	protected void preload(HttpServletRequest request)
	{
	log.debug("prload debug started");
	CourseModel cModel=new CourseModel();
	
	try 
	{
	List cList=cModel.list();

	request.setAttribute("courseList", cList);

	
	} 
	catch (Exception e) 
	{
	e.printStackTrace();
	}
	
	log.debug("preload debug completed");
	}
	
	
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request)
	{
		log.debug("SubjectListCtl populatebean debug started");
		
		SubjectBean bean=new SubjectBean();
		
		bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
		
		bean.setSubjectName(DataUtility.getStringData(request.getParameter("subjectName")));
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		
		log.debug("SubjectListCtl populate bean debug completed");
		
		return bean;
	}

    /**
     * Contains Display logics
     */
	@Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
    {
			
	log.debug("SubjectListCtl doget debug started");
	List list = null;
	int pageNo=1;
	int pageSize=DataUtility.getInt(PropertyReader.getValue("page.size"));
	
	SubjectBean bean= (SubjectBean)populateBean(request);
	
	String op=DataUtility.getString(request.getParameter("operation"));
	

	
	 String[] ids = request.getParameterValues("ids");
	
	//System.out.println("Heeeee"+ids[0]);
	
	SubjectModel model=new SubjectModel();
	try 
	{
		try 
		{
		list=model.search(bean, pageNo, pageSize);
		
		if(list==null || list.size()==0)
		{
			ServletUtility.setErrorMessage("record not found", request);
			request.setAttribute("next", list);
			return;
		}
		}
		catch (SQLException e) 
		{
		throw new ApplicationException("exception in doget search");	
		}
		ServletUtility.setList(list, request);
		ServletUtility.setPageNo(pageNo, request);
		ServletUtility.setPageSize(pageSize, request);
		ServletUtility.forward(getView(), request, response);
	} 
	catch (Exception e) 
	{
	log.error(e);
	ServletUtility.handleException(e, request, response);
	return;
	}
	log.debug("SubjectListCtl doget debug completed");
    }

    /**
     * Contains submit logics
     */
	@Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
    	log.debug("courseListctl debug started");
    	List list=null;
    	int pageNo=DataUtility.getInt(request.getParameter("pageNo"));
    	int pageSize=DataUtility.getInt(request.getParameter("pageSize"));

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
        
        String op=DataUtility.getString(request.getParameter("operation"));
        String ids[]=request.getParameterValues("ids");
        
        SubjectBean bean=(SubjectBean)populateBean(request);
        SubjectModel model=new SubjectModel();
        
        try 
        {
			if(OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)|| "Previous".equalsIgnoreCase(op))
			{
				if(OP_SEARCH.equalsIgnoreCase(op))
				{
					pageNo=1;
				}
				else if(OP_NEXT.equalsIgnoreCase(op))
				{
					pageNo++;
				}
				else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo>1) 
				{
				   pageNo--;	
				}
			}
			else if (OP_NEW.equalsIgnoreCase(op)) 
			{
			ServletUtility.redirect(ORSView.SUBJECT_CTL, request, response);	
			return;
			}
			else if(OP_BACK.equalsIgnoreCase(op))
			{
				ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
				return;
			}
			else if(OP_DELETE.equalsIgnoreCase(op))
			{
			pageNo=1;
			if(ids!=null && ids.length>0)
			{
			 SubjectBean deleteBean=new SubjectBean();
		     for (String id : ids) 
		     {
			  deleteBean.setId(DataUtility.getInt(id));
			     model.delete(deleteBean);
			     bean.setId(0);
			 }
		     ServletUtility.setSuccessMessage( "Data successfully deleted", request);	     
			}
			else
			{
				ServletUtility.setErrorMessage("select atleast one record", request);	
			}
			}
			else if(OP_RESET.equalsIgnoreCase(op))
			{
			ServletUtility.redirect(ORSView.SUBJECT_LIST_CTL, request, response);
			return;
			}
			try 
			{
		     
			list = model.search(bean, pageNo, pageSize);
			ServletUtility.setBean(bean, request);         //// to set value in preload
			
			if(list==null || list.size()==0)
			{
			 ServletUtility.setErrorMessage("No Record Found...!!!", request);
			 request.setAttribute("next", list);
			}
			
			} 
			catch (Exception e) 
			{
			e.printStackTrace();	
			}
				
			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
        } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        log.debug("courseListctl debug completed");

    }

	@Override
	protected String getView() 
	{
	return ORSView.SUBJECT_LIST_VIEW;
	}

}
