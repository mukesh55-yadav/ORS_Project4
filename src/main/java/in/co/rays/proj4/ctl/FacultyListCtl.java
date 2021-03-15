package in.co.rays.proj4.ctl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.FactoryConfigurationError;

import org.apache.log4j.Logger;

import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.FacultyBean;

import in.co.rays.proj4.model.FacultyModel;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * faculty List functionality Controller. Performs operation for list, search
 * and delete operations of faculty
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */
@WebServlet("/ctl/FacultyListCtl")
public class FacultyListCtl extends BaseCtl
{
	 private static Logger log = Logger.getLogger(FacultyListCtl.class);

	@Override
	 protected BaseBean populateBean(HttpServletRequest request) 
	 {
	 log.debug("faculty list ctl debug started");
	 
	 FacultyBean bean=new FacultyBean();
	 
	 bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
	
	  bean.setLastName(DataUtility.getString(request.getParameter("lastName")));
	
	 bean.setLoginId(DataUtility.getString(request.getParameter("loginId")));
	 
	 bean.setId(DataUtility.getLong(request.getParameter("id")));
	
	 
	 log.debug("faculty list ctl debug completed");
	 
	 return bean;
	 
	 }

    /**
     * Contains display logic
     */
	@Override
	 protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	        log.debug("FacultyListCtl doGet Start");
	        
	        List list = null;
	        int pageNo = 1;
	        
	        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
	        FacultyBean bean = (FacultyBean) populateBean(request);
	        String op = DataUtility.getString(request.getParameter("operation"));
	        
	        System.out.println("faculty list ctl do get"+op);
	        
	    
	        // get the selected checkbox ids array for delete list
	        String[] ids = request.getParameterValues("chk_1");   //to get muliple ids through check box //like movie tkt booking seats are check box
	        FacultyModel model = new FacultyModel();
	        try {
	            try {
					list = model.search(bean, pageNo, pageSize);
		            if (list == null || list.size() == 0) {
		            	ServletUtility.setErrorMessage("No Record found...!!!! ", request);
		            	request.setAttribute("next",list);
		              }
			
				} catch (SQLException e) {
				throw new ApplicationException("exception in facultylist doget");
			     //e.printStackTrace();
				}
		        System.out.println("faculty list ctl do get"+list.size());

	            ServletUtility.setList(list, request);
	            ServletUtility.setPageNo(pageNo, request);
	            ServletUtility.setPageSize(pageSize, request);
	            ServletUtility.forward(getView(), request, response);
	        } catch (Exception e) {
	            log.error(e);
	            ServletUtility.handleException(e, request, response);
	            return;
	        }
	        log.debug("FacultyListCtl doPOst End");
	    }

    /**
     * Contains submit logic
     */
	   @Override
	    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	    	 
		     log.debug("FacultyListCtl doPost Start");
	    	 
	         List list = null;
	         
	         int pageNo = DataUtility.getInt(request.getParameter("pageNo"));//coming from view
	         
	         //System.out.println("hi doget of fac ctl"+pageNo);
	         
	         int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

	         pageNo = (pageNo == 0) ? 1 : pageNo;
	         pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
	         
	         FacultyBean bean = (FacultyBean) populateBean(request);
	         String op = DataUtility.getString(request.getParameter("operation"));
	         
	        // System.out.println("hi dopost of fac ctl"+bean.getFirstName()+bean.getLastName());
	         
	         
	         // get the selected checkbox ids array for delete list
	         String[] ids = request.getParameterValues("ids");
	         
	         FacultyModel model = new FacultyModel();
	         try {

	             if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)|| "Previous".equalsIgnoreCase(op)) 
	             {
	                 if (OP_SEARCH.equalsIgnoreCase(op))
	                 {
	                	 
	                     pageNo = 1;
	                	 //System.out.println("hi doget of fac ctl inside search"+pageNo);
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
	                 ServletUtility.redirect(ORSView.FACULTY_CTL, request, response);
	                 return;
	             } else if (OP_DELETE.equalsIgnoreCase(op)) 
	             {
	            	 
	                 pageNo = 1;
	                 if (ids != null && ids.length > 0) {
	                     FacultyBean deletebean = new FacultyBean();
	                     for (String id : ids) {
	                         deletebean.setId(DataUtility.getInt(id));
	                         try {
								model.delete(deletebean);
								bean.setId(0);    //// it is solution to avoid delete bug
							} 
	                         catch (SQLException e) 
	                         {
								e.printStackTrace();
							 }
	                         
	                     }
	                     ServletUtility.setSuccessMessage( "Data successfully deleted", request);
	                     
	                 } else {
	                     ServletUtility.setErrorMessage( "Select at least one record", request);
	                 }
	             }
	             else if(OP_RESET.equalsIgnoreCase(op))
	             {
	              ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response);
	              return;
	             }
	             else if(OP_BACK.equalsIgnoreCase(op))
	             {
	              ServletUtility.redirect(ORSView.FACULTY_LIST_CTL, request, response); 
	              return;
	             }
	             try {
	            	
				     list =model.search(bean, pageNo, pageSize);

		               if (list == null || list.size() == 0) 
		               {
		            	   
			                ServletUtility.setErrorMessage("Record not found ...!!!!", request);
			                request.setAttribute("list", list);
			                
			             }
	             } 
	             catch (SQLException e) 
	             {
					// TODO Auto-generated catch block
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
	         log.debug("facultyListCtl doGet End");
	     }


	@Override
	protected String getView() {
	
		return ORSView.FACULTY_LIST_VIEW;
	}

}
