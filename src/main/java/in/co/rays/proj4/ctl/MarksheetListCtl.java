package in.co.rays.proj4.ctl;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.MarksheetModel;
import in.co.rays.proj4.model.StudentModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Marksheet List functionality Controller. Performs operation for list, search
 * and delete operations of Marksheet
 * 
 * Servlet implementation class MarksheetlistCtl
 * 
 * @author SunilOS
 * @version 1.0
 * @Copyright (c) SunilOS
 */


@ WebServlet(name="MarksheetListCtl",urlPatterns={"/ctl/MarksheetListCtl"})
public class MarksheetListCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(MarksheetListCtl.class);
	 
	 @Override
	 protected void preload(HttpServletRequest request) 
	 {
		 StudentModel StuModel=new StudentModel();
		 StudentBean StuBean=new StudentBean();
		 try 
		 {
			List list=StuModel.list();
			request.setAttribute("studentList", list);
		 } 
		 catch (ApplicationException e)
		 {
	       e.printStackTrace();
		 }
		 	 
	 }

	    @Override
	    protected BaseBean populateBean(HttpServletRequest request) {
	        MarksheetBean bean = new MarksheetBean();

	        bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));

	        bean.setName(DataUtility.getString(request.getParameter("name")));
	        
	        bean.setStudentId(DataUtility.getLong(request.getParameter("subjetId")));

	        return bean;
	    }

	    /**
	     * ContainsDisplaylogics
	     */
	    protected void doGet(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {
	        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
	        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

	        pageNo = (pageNo == 0) ? 1 : pageNo;

	        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
	                .getValue("page.size")) : pageSize;

	        MarksheetBean bean = (MarksheetBean) populateBean(request);

	        List list = null;
	        MarksheetModel model = new MarksheetModel();
	        try {
	            list = model.search(bean, pageNo, pageSize);
	        } catch (ApplicationException e) {
	            log.error(e);
	            ServletUtility.handleException(e, request, response);
	            return;
	        }

	        if (list == null || list.size() == 0) {
	            ServletUtility.setErrorMessage("No Record found...!!! ", request);
	        }
	        ServletUtility.setList(list, request);
	        ServletUtility.setPageNo(pageNo, request);
	        ServletUtility.setPageSize(pageSize, request);
	        ServletUtility.forward(getView(), request, response);
	        log.debug("MarksheetListCtl doGet End");

	    }

	    /**
	     * Contains Submit logics
	     */
	    @Override
	    protected void doPost(HttpServletRequest request,
	            HttpServletResponse response) throws ServletException, IOException {

	        log.debug("MarksheetListCtl doPost Start");

	        List list = null;

	        int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
	        int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

	        pageNo = (pageNo == 0) ? 1 : pageNo;

	        pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader
	                .getValue("page.size")) : pageSize;

	        MarksheetBean bean = (MarksheetBean) populateBean(request);

	        String op = DataUtility.getString(request.getParameter("operation"));

	        // get the selected checkbox ids array for delete list
	        String[] ids = request.getParameterValues("ids");

	        MarksheetModel model = new MarksheetModel();

	        try {

	            if (OP_SEARCH.equalsIgnoreCase(op) || OP_NEXT.equalsIgnoreCase(op)
	                    || OP_PREVIOUS.equalsIgnoreCase(op)) {

	                if (OP_SEARCH.equalsIgnoreCase(op)) {
	                    pageNo = 1;
	                } else if (OP_NEXT.equalsIgnoreCase(op)) {
	                    pageNo++;
	                } else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
	                    pageNo--;
	                }

	            } else if (OP_NEW.equalsIgnoreCase(op)) {
	                ServletUtility.redirect(ORSView.MARKSHEET_CTL, request,
	                        response);
	                return;
	            } else if (OP_DELETE.equalsIgnoreCase(op)) {
	                pageNo = 1;
	                if (ids != null && ids.length > 0) {
	                    MarksheetBean deletebean = new MarksheetBean();
	                    for (String id : ids) {
	                        deletebean.setId(DataUtility.getInt(id));
	                        model.delete(deletebean);
	                        ServletUtility.setSuccessMessage("Data deleted successfully", request);
	                       
	                    }
	                } else {
	                    ServletUtility.setErrorMessage(
	                            "Select at least one record", request);
	                }
	            }
	            else if(OP_RESET.equalsIgnoreCase(op))
	            {
	            	ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
	            	return;
	            }
	            else if(OP_BACK.equalsIgnoreCase(op))
	            {
	            	ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request, response);
	            	return;
	            }
	            list = model.search(bean, pageNo, pageSize);
	            ServletUtility.setBean(bean, request);
	            ServletUtility.setList(list, request);
	            if (list == null || list.size() == 0) {
	                ServletUtility.setErrorMessage("No record found..!!! ", request);
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

	        log.debug("MarksheetListCtl doPost End");
	    }

	@Override
	protected String getView() {
		return ORSView.MARKSHEET_LIST_VIEW;
	}

}