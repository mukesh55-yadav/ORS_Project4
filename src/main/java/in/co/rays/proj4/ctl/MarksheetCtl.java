package in.co.rays.proj4.ctl;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.model.MarksheetModel;
import in.co.rays.proj4.model.StudentModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * Marksheet functionality Controller. Performs operation for add, update,
 * delete and get Marksheet
 * */
@ WebServlet(name="MarksheetCtl",urlPatterns={"/ctl/MarksheetCtl"})
public class MarksheetCtl extends BaseCtl
{
	 private static Logger log = Logger.getLogger(MarksheetCtl.class);

	    @Override
	    protected void preload(HttpServletRequest request) {
	        StudentModel model = new StudentModel();
	        try {
	            List l = model.list();
	            request.setAttribute("studentList", l);
	        } catch (ApplicationException e) {
	            log.error(e);
	        }

	    }

	    @Override
	    protected boolean validate(HttpServletRequest request) {

	        log.debug("MarksheetCtl Method validate Started");

	        boolean pass = true;

	        if (DataValidator.isNull(request.getParameter("rollNo"))) 
	        {
	            request.setAttribute("rollNo",PropertyReader.getValue("error.require", "Roll Number"));
	            pass = false;
	        }else if (!DataValidator.isNameNumber(request.getParameter("rollNo"))) {
	            request.setAttribute("rollNo",
	                    PropertyReader.getValue("error.pass", "Roll No."));
	            pass = false;
	        }

	        if (DataValidator.isNull(request.getParameter("physics"))) {
	            request.setAttribute("physics",PropertyReader.getValue("error.require", "Marks"));
	            pass = false;
	        }
	        else if(!DataValidator.isInteger(request.getParameter("physics")))
	        {
	        	request.setAttribute("physics", PropertyReader.getValue("error.integer", "Physics Marks"));
	        	pass=false;
	        }
	        
	        else if ((DataUtility.getInt(request.getParameter("physics")) > 100)) {
	            request.setAttribute("physics",PropertyReader.getValue("error.marks", "Physics Marks"));
	            pass = false;
	        }

	        else if ((DataUtility.getInt(request.getParameter("physics")) < 0)) {
	            request.setAttribute("physics",PropertyReader.getValue("error.mark", "Physics Marks"));
	            pass = false;
	        }
	        
	        if (DataValidator.isNull(request.getParameter("chemistry"))) {
	            request.setAttribute("chemistry",PropertyReader.getValue("error.require", "Marks"));
	            pass = false;
	        }
	        else if( !DataValidator.isInteger(request.getParameter("chemistry")))
	        {
	        	request.setAttribute("chemistry", PropertyReader.getValue("error.integer", "Chemistry Marks"));
	        	pass=false;
	        }

	        else if (DataUtility.getInt(request.getParameter("chemistry")) > 100 ) {
	            request.setAttribute("chemistry", PropertyReader.getValue("error.marks", "Chemistry Marks"));
	            pass = false;
	        }
	        else if ((DataUtility.getInt(request.getParameter("chemistry")) < 0)) {
	            request.setAttribute("chemistry",PropertyReader.getValue("error.mark", "Chemistry Marks"));
	            pass = false;
	        }

	        if (DataValidator.isNull(request.getParameter("maths"))) {
	            request.setAttribute("maths",PropertyReader.getValue("error.require", "Maths"));
	            pass = false;
	        }

	        else  if (DataUtility.getInt(request.getParameter("maths")) > 100) {
	            request.setAttribute("maths",PropertyReader.getValue("error.marks", "Maths Marks"));
	            pass = false;
	        }
	        else if( !DataValidator.isInteger(request.getParameter("maths")))
	        {
	        	request.setAttribute("maths", PropertyReader.getValue("error.integer", "Maths Marks"));
	        	pass=false;
	        }
	        else if ((DataUtility.getInt(request.getParameter("maths")) < 0)) {
	            request.setAttribute("maths",PropertyReader.getValue("error.mark", "Marks Marks"));
	            pass = false;
	        }

	        if (DataValidator.isNull(request.getParameter("studentId"))) {
	            request.setAttribute("studentId",PropertyReader.getValue("error.require", "Student Name"));
	            pass = false;
	        }

	        log.debug("MarksheetCtl Method validate Ended");

	        return pass;
	    }

	    @Override
	    protected BaseBean populateBean(HttpServletRequest request) {

	        log.debug("MarksheetCtl Method populatebean Started");

	        MarksheetBean bean = new MarksheetBean();

	        bean.setId(DataUtility.getLong(request.getParameter("id")));

	        bean.setRollNo(DataUtility.getString(request.getParameter("rollNo")));

	        bean.setName(DataUtility.getString(request.getParameter("name")));

	        bean.setPhysics(DataUtility.getString(request.getParameter("physics")));
	        
           // System.out.println("marksheet ctl populate bean"+bean.getPhysics());
            
	        bean.setChemistry(DataUtility.getString(request.getParameter("chemistry")));
	        
	       // System.out.println("marksheet ctl populate bean"+bean.getChemistry());
	        
	        bean.setMaths(DataUtility.getString(request.getParameter("maths")));
	        
	       // System.out.println("marksheet ctl populate bean"+bean.getMaths());
	        
	        bean.setStudentId(DataUtility.getLong(request.getParameter("studentId")));

	        populateDTO(bean, request);

	        System.out.println("Population done");

	        log.debug("MarksheetCtl Method populatebean Ended");

	        return bean;
	    }

	    /**
	     * Contains Display logics
	     */
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        log.debug("MarksheetCtl Method doGet Started");
             //System.out.println("marksheet ctl doget id");
	        String op = DataUtility.getString(request.getParameter("operation"));
	        MarksheetModel model = new MarksheetModel();
	        long id = DataUtility.getLong(request.getParameter("id"));
	        if (id > 0) {
	        	//System.out.println("marksheet ctl doget id"+id);
	            MarksheetBean bean;
	            try {
	                bean = model.findByPK(id);
	                ServletUtility.setBean(bean, request);
	            } catch (ApplicationException e) {
	                log.error(e);
	                ServletUtility.handleException(e, request, response);
	                return;
	            }
	        }
	        ServletUtility.forward(getView(), request, response);
	        log.debug("MarksheetCtl Method doGet Ended");
	    }

	    /**
	     * Contains Submit logics
	     */
	    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {

	        log.debug("MarksheetCtl Method doPost Started");

	        String op = DataUtility.getString(request.getParameter("operation"));
	        // get model
	        MarksheetModel model = new MarksheetModel();

	        long id = DataUtility.getLong(request.getParameter("id"));

	        if (OP_SAVE.equalsIgnoreCase(op) || OP_UPDATE.equalsIgnoreCase(op)) {

	            MarksheetBean bean = (MarksheetBean) populateBean(request);
	            try {
	                if (id > 0) {
	                    model.update(bean);

		                ServletUtility.setBean(bean, request);
		                ServletUtility.setSuccessMessage("Data is successfully updated",request);
		                ServletUtility.forward(getView(), request, response);
	                } else {
	                    long pk = model.add(bean);
	                    bean.setId(0);

		                ServletUtility.setBean(bean, request);
		                ServletUtility.setSuccessMessage("Data is successfully saved",request);
		                ServletUtility.forward(getView(), request, response);
	                }

	            } catch (ApplicationException e) {
	                log.error(e);
	                ServletUtility.handleException(e, request, response);
	                return;
	            } catch (DuplicateRecordException e) {
	                ServletUtility.setBean(bean, request);
	                ServletUtility.setErrorMessage("Roll no already exists",request);
	            }

	        } else if (OP_DELETE.equalsIgnoreCase(op)) {

	            MarksheetBean bean = (MarksheetBean) populateBean(request);
	            
	            try 
	            {
	                model.delete(bean);
	                ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request,response);
	                return;
	            } 
	            catch (ApplicationException e) 
	            {
	                log.error(e);
	                ServletUtility.handleException(e, request, response);
	                return;
	            }

	        } 
	        else if (OP_CANCEL.equalsIgnoreCase(op)) 
	        {
             ServletUtility.redirect(ORSView.MARKSHEET_LIST_CTL, request,response);
	         return;
           }
	        else if(OP_RESET.equalsIgnoreCase(op))
	        {
	        	ServletUtility.redirect(ORSView.MARKSHEET_CTL, request, response);
	        	return;
	        }
	        
	        ServletUtility.forward(getView(), request, response);

	        log.debug("MarksheetCtl Method doPost Ended");
	    }

		@Override
		protected String getView() 
		{
		return ORSView.MARKSHEET_VIEW;
		}
}
