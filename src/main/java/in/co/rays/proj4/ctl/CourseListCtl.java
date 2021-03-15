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
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;
/**
 * course List functionality Controller. Performs operation for list, search
 * and delete operations of course
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet(name="CourseListCtl",urlPatterns="/ctl/CourseListCtl")
public class CourseListCtl extends BaseCtl 
{
	private static Logger log=Logger.getLogger(CourseListCtl.class);
	
	@Override
	protected BaseBean populateBean(HttpServletRequest request)
	{
		
	log.debug("CourseListCtl populate bean debug started");
	
	CourseBean bean=new CourseBean();
	
	bean.setCourseName(DataUtility.getStringData(request.getParameter("courseName")));
	
	bean.setDescription(DataUtility.getStringData(request.getParameter("description")));
	
	bean.setId(DataUtility.getLong(request.getParameter("id")));
	
	log.debug("CourseListCtl populate bean debug completed");
	
	return bean;

	}

    /**
     * Contains display logic
     */
	@Override

    protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
		{
		log.debug("CourseListCtl doGet Start");
		    List list = null;
	        int pageNo = 1;
	        
	        //System.out.println("hi i m userlist ctl doget ");
	        
	        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
	        CourseBean bean = (CourseBean) populateBean(request);
	        String op = DataUtility.getString(request.getParameter("operation"));
	        
	        //System.out.println("i m in doget of userctl operation"+op);
	        // get the selected checkbox ids array for delete list
	        String[] ids = request.getParameterValues("ids");   //to get muliple ids through check box //like movie tkt booking seats are check box
	        CourseModel model = new CourseModel();
	        try {
	            try {
					list = model.search(bean, pageNo, pageSize);
			
				} catch (SQLException e) {
				throw new ApplicationException("exception in coursectl doget");
					//e.printStackTrace();
				}
	            
	            if (list == null || list.size() == 0) {
	            	ServletUtility.setErrorMessage("No Record found...!!!!", request);
	            	request.setAttribute("next",list);
	             }
	            ServletUtility.setList(list, request);
	            ServletUtility.setPageNo(pageNo, request);
	            ServletUtility.setPageSize(pageSize, request);
	            ServletUtility.forward(getView(), request, response);
	        } catch (ApplicationException e) {
	            log.error(e);
	            ServletUtility.handleException(e, request, response);
	            return;
	        }
	        log.debug("courseListCtl doPOst End");

			}

    /**
     * Contains submit logic
     */
	@Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
	    log.debug("courseListctl debug started");
        List list = null;
       
        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));    //coming from jsp page
        
        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
        

        pageNo = (pageNo == 0) ? 1 : pageNo;
        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
        CourseBean bean = (CourseBean) populateBean(request);
        String op = DataUtility.getString(request.getParameter("operation"));
        
        
        String[] ids = request.getParameterValues("ids");
        
        CourseModel model = new CourseModel();
        try {

            if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)|| "Previous".equalsIgnoreCase(op)) 
            {
                if (OP_SEARCH.equalsIgnoreCase(op))
                {
                    pageNo = 1;
                }
                else if (OP_NEXT.equalsIgnoreCase(op)) 
                {
                    pageNo++;
                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) 
                {
                    pageNo--;
                }

            } else if (OP_NEW.equalsIgnoreCase(op)) 
            {
                ServletUtility.redirect(ORSView.COURSE_CTL, request, response);
                return;
            } 
            else if (OP_DELETE.equalsIgnoreCase(op)) 
            {
           	     pageNo = 1;
                if (ids != null && ids.length > 0) {
                    CourseBean deletebean = new CourseBean();
                    for (String id : ids) {
                        deletebean.setId(DataUtility.getInt(id));
                        try {
							model.delete(deletebean);
						} 
                        catch (SQLException e) 
                        {
							e.printStackTrace();
						 }
                        ServletUtility.setSuccessMessage( "Data successfully deleted", request);
                    }
                } else {
                    ServletUtility.setErrorMessage( "Select at least one record", request);
                }
            }
            else if(OP_RESET.equalsIgnoreCase(op))
            {
            	ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
            	return;
            }
            else if(OP_BACK.equalsIgnoreCase(op))
            {
            	ServletUtility.redirect(ORSView.COURSE_LIST_CTL, request, response);
            	return;
            }
            try {
				list = model.search(bean, pageNo, pageSize);

	            if (list == null || list.size() == 0) {
	                ServletUtility.setErrorMessage("No Record found...!!!", request);
	                request.setAttribute("next",list);
	               
	            }
			    } catch (SQLException e) {
				e.printStackTrace();
			}
            ServletUtility.setList(list, request);
            ServletUtility.setPageNo(pageNo, request);
            ServletUtility.setPageSize(pageSize, request);
            ServletUtility.forward(getView(), request, response);
            } catch (Exception e) {
            log.error(e);
            ServletUtility.handleException(e, request, response);
            return;
        }
		log.debug("courseListctl debug completed");
    }

	@Override
	protected String getView() 
	{
	return ORSView.COURSE_LIST_VIEW;
	}
;;
}
