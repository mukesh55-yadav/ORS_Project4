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
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.UserModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;
/**
 * User List functionality Controller. Performs operation for list, search and
 * delete operations of User
 * 
 */
@ WebServlet(name="UserListCtl",urlPatterns={"/ctl/UserListCtl"})
public class UserListCtl extends BaseCtl
{
	 private static Logger log = Logger.getLogger(UserListCtl.class);
	 
	 
	   @Override
	     protected void preload(HttpServletRequest request) 
	    {
	      log.debug("user list ctl preload started"); 	
	      
	      RoleModel roleModel=new RoleModel();
	    	try
	    	{
				List roleList= (List)roleModel.list();
				request.setAttribute("roleList",roleList);
			} 
	    	catch (ApplicationException e) 
	    	{
			e.printStackTrace();
			}
	      
	      log.debug("user list ctl preload completed"); 
	    	
	    	
	    }
	    

	    @Override
	    protected BaseBean populateBean(HttpServletRequest request) {
	        UserBean bean = new UserBean();
	        
           // System.out.println("hi i m userlist ctl populate bean");
            
	        bean.setFirstName(DataUtility.getString(request.getParameter("firstName")));
	        
	        //System.out.println("hi i m userlist ctl populate bean"+bean.getFirstName());
	        
	        bean.setId(DataUtility.getLong(request.getParameter("chk_1")));
	        
	        //System.out.println("hi i m userlist ctl populate bean"+bean.getId());
	        
	        bean.setLastName(DataUtility.getString(request.getParameter("lastName")));

	        bean.setLogin(DataUtility.getString(request.getParameter("login")));
	        
	        bean.setRoleId(DataUtility.getLong(request.getParameter("roleId")));
	        
	       // System.out.println("hi i m userlist ctl populate bean"+bean.getLogin());
	        return bean;
	    }

	    /**
	     * Contains Display logics
	     */
	    protected void doGet(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
	        log.debug("UserListCtl doGet Start");
	        List list = null;
	        int pageNo = 1;
	        
	        
	        
	        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
	        UserBean bean = (UserBean) populateBean(request);
	        String op = DataUtility.getString(request.getParameter("operation"));
	        
	        //System.out.println("i m in doget of userctl operation"+op);
	        // get the selected checkbox ids array for delete list
	        String[] ids = request.getParameterValues("ids");   //to get muliple ids through check box //like movie tkt booking seats are check box
	        UserModel model = new UserModel();
	        try {
	            list = model.search(bean, pageNo, pageSize);
	            ServletUtility.setList(list, request);
	            if (list == null || list.size() == 0) {
	            	ServletUtility.setErrorMessage("No Record found..!!!! ", request);
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
	        log.debug("UserListCtl doPOst End");
	    }

	    /**
	     * Contains Submit logics
	     */
	    @Override
	    protected void doPost(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
	    	 log.debug("UserListCtl doPost Start");
	         List list = null;
	        
	         int pageNo = DataUtility.getInt(request.getParameter("pageNo"));    //coming from jsp page
	        
	         int pageSize = DataUtility.getInt(request.getParameter("pageSize"));
	        

	         pageNo = (pageNo == 0) ? 1 : pageNo;
	         pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
	         UserBean bean = (UserBean) populateBean(request);
	         String op = DataUtility.getString(request.getParameter("operation"));
	      
	         // get the selected checkbox ids array for delete list
	         String[] ids = request.getParameterValues("ids");
	         
	         UserModel model = new UserModel();
	         try {

	             if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op)
	                     || "Previous".equalsIgnoreCase(op)) 
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
	                 ServletUtility.redirect(ORSView.USER_CTL, request, response);
	                 return;
	             } 
	             
	             else if (OP_DELETE.equalsIgnoreCase(op)) 
	             {
	            	 
	                 pageNo = 1;
	                 if (ids != null && ids.length > 0) {
	                     UserBean deletebean = new UserBean();
	                    
	                     for (String id : ids) {
	                         deletebean.setId(DataUtility.getInt(id));
	                         model.delete(deletebean);
							 bean.setId(0);
	                     }

	                     ServletUtility.setSuccessMessage( "Data successfully deleted", request);
	                     
	                     
	                 } 

	                 else {
	                     ServletUtility.setErrorMessage( "Select at least one record", request);
	                 }
	             }
	             else if(OP_RESET.equalsIgnoreCase(op))
	             {
	             ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);	 
	             return;
	             }
	             else if(OP_BACK.equalsIgnoreCase(op))
	             {
	            	 ServletUtility.redirect(ORSView.USER_LIST_CTL, request, response);
	            	 return;
	             }
	             list = model.search(bean, pageNo, pageSize);
				 ServletUtility.setBean(bean, request);
				    if (list == null || list.size() == 0) {
				    
				     ServletUtility.setErrorMessage("No Record found...!!! ", request);
				     request.setAttribute("next",list);
				     
				 }
	             
	             ServletUtility.setList(list, request);
	             ServletUtility.setPageNo(pageNo, request);
	             ServletUtility.setPageSize(pageSize, request);
	             System.out.println("page no"+pageNo+pageSize);
	             ServletUtility.forward(getView(), request, response);

	         } catch (ApplicationException e) {
	             log.error(e);
	             ServletUtility.handleException(e, request, response);
	             return;
	         }
	         log.debug("UserListCtl doGet End");
	     }



	@Override
	protected String getView() {
		
		return ORSView.USER_LIST_VIEW;
	}

}
