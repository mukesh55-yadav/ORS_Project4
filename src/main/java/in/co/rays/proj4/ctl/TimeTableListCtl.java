package in.co.rays.proj4.ctl;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.FacultyBean;
import in.co.rays.proj4.bean.TimeTableBean;
import in.co.rays.proj4.model.CourseModel;
import in.co.rays.proj4.model.SubjectModel;
import in.co.rays.proj4.model.TimeTableModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

/**
 * timetable List functionality Controller. Performs operation for list, search
 * and delete operations of timetable
 * 
 */
@ WebServlet(urlPatterns={"/ctl/TimeTableListCtl"})
public class TimeTableListCtl extends BaseCtl
{

	
	private static Logger log = Logger.getLogger(TimeTableListCtl.class);

	@Override
	 protected void preload(HttpServletRequest request) 
	{
	 log.debug("TimeTableList ctl preload debug started");
		
     SubjectModel subModel=new SubjectModel();
     CourseModel couModel=new CourseModel();
     try 
     {
		List subList = subModel.list();
		List couList = couModel.list();
		
		request.setAttribute("subjectList",subList);
		request.setAttribute("courseList",couList);
	 }
     catch (Exception e)
     {
	 e.printStackTrace();
	 }
     log.debug("TimeTableList ctl preload debug completed");
	}
	
	
	@Override
	 protected BaseBean populateBean(HttpServletRequest request) 
	 {
	 log.debug("timetable list ctl debug started");
	 
	 TimeTableBean bean=new TimeTableBean();
	 
     bean.setCourseName(DataUtility.getString(request.getParameter("courseName")));
     
	 bean.setCourseId(DataUtility.getLong(request.getParameter("courseId")));
	
	 
	 bean.setSubjectId(DataUtility.getLong(request.getParameter("subjectId")));
	 
	 bean.setSubjectName(DataUtility.getString(request.getParameter("subjectName")));
	 
	 bean.setExamDate(DataUtility.getDate(request.getParameter("examDate")));
	
     bean.setId(DataUtility.getLong(request.getParameter("id")));
	
	 
	 log.debug("timetable list ctl debug completed");
	 
	 return bean;
	 
	 }

    /**
     * Contains Display logics
     */
	@Override
	 protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	        log.debug("timetable list doGet Started");
	        
	        List list = null;
	        int pageNo = 1;
	        
	        int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
	        TimeTableBean bean = (TimeTableBean) populateBean(request);
	        String op = DataUtility.getString(request.getParameter("operation"));
	        
	        //System.out.println("heyyy doget "+op);
	        
	    
	        // get the selected checkbox ids array for delete list
	        String[] ids = request.getParameterValues("ids");  //to get muliple ids through check box //like movie tkt booking seats are check box
	        TimeTableModel model = new TimeTableModel();
	        try {
	            try {
					list = model.search(bean, pageNo, pageSize);
			        
				} catch (SQLException e) {
				//throw new ApplicationException("exception in facultylist doget");
			     e.printStackTrace();
				}
	            ServletUtility.setList(list, request);
	            if (list == null || list.size() == 0) {
	            	ServletUtility.setErrorMessage("No Record found...!!!! ", request);
	            	request.setAttribute("next",list);
	                return;         
	            }
	            ServletUtility.setBean(bean, request);
	            ServletUtility.setList(list, request);
	            ServletUtility.setPageNo(pageNo, request);
	            ServletUtility.setPageSize(pageSize, request);
	            ServletUtility.forward(getView(), request, response);
	        } catch (Exception e) {
	            log.error(e);
	            ServletUtility.handleException(e, request, response);
	            return;
	        }
	        log.debug("timetablelist doPOst End");
	    }

    /**
     * Contains submit logics
     */
	   @Override
	    protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
	    	 
		     log.debug("TimetableListCtl doPost Started");
	    	 
	         List list = null;
	         
	         int pageNo = DataUtility.getInt(request.getParameter("pageNo"));//coming from view
	         
	         //System.out.println("hi doget of fac ctl"+pageNo);
	         
	         int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

	         pageNo = (pageNo == 0) ? 1 : pageNo;
	         pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;
	         
	         TimeTableBean bean = (TimeTableBean) populateBean(request);
	         String op = DataUtility.getString(request.getParameter("operation"));
	         
	         System.out.println("time table list ctl dopost"+op);
	         
	         
	         // get the selected checkbox ids array for delete list
	         String[] ids = request.getParameterValues("ids");
	         
	         //System.out.println("time table list ctl dopost"+ids[0]);
	         
	         TimeTableModel model = new TimeTableModel();
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
	                 ServletUtility.redirect(ORSView.TIME_TABLE_CTL, request, response);
	                 return;
	             } else if (OP_DELETE.equalsIgnoreCase(op)) 
	             {
	            	 
	                 pageNo = 1;
	                 if (ids != null && ids.length > 0) {
	                     TimeTableBean deletebean = new TimeTableBean();
	                     for (String id : ids) {
	                         deletebean.setId(DataUtility.getInt(id));
	                   
							 model.delete(deletebean);
                       
	                         ServletUtility.setSuccessMessage( "Data successfully deleted", request);
	                     }
	                 }
	                 else 
	                 {
	                     ServletUtility.setErrorMessage( "Select at least one record", request);
	                 }
	             }
	             else if(OP_BACK.equalsIgnoreCase(op))
	             {
	              ServletUtility.redirect(ORSView.TIME_TABLE_LIST_CTL, request, response); 
	              return;
	             }
	             else if(OP_RESET.equalsIgnoreCase(op))
	             {
	            ServletUtility.redirect(ORSView.TIME_TABLE_LIST_CTL, request, response);
	            return;
	             }
	             try {
				     list =model.search(bean, pageNo, pageSize);
				     
				     ServletUtility.setBean(bean, request);   //// to set value in preload after search
				     
				     if (list == null || list.size() == 0) {
		             ServletUtility.setErrorMessage("No Record found...!!!! ", request);
		             request.setAttribute("next",list);
		                    
		             }
					} catch (SQLException e) {
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
	         log.debug("timetable  dopost End");
	     }

	@Override
	protected String getView() {
	return ORSView.TIME_TABLE_LIST_VIEW;
	}

}
