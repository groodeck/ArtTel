//package org.arttel.servlet;
//
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.log4j.Logger;
//import org.arttel.controller.BaseController;
//import org.arttel.exception.UserNotLoggedException;
//import org.arttel.mapper.ControlMapper;
//
///**
// * Servlet implementation class Controller
// */
//public class MainServlet extends HttpServlet  {
//	
//	Logger log = Logger.getLogger(MainServlet.class);
//	private static final long serialVersionUID = 1L;
//
//	/**
//	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
//	 */
//    @SuppressWarnings("unused")
//	public void service( final HttpServletRequest request, final HttpServletResponse response ) throws ServletException, IOException {
//		
//    	setRequestEncoding(request);
//    	
//    	final String targetCode = getTarget( request );
//    	final String event = request.getParameter("event");
//		log.info("Request received: " + targetCode + " [event: " + event + "]");
//		
//		BaseController controller = ControlMapper.getControllerByAction( targetCode, request.getServletContext());
//		if(controller != null){
//			 try {
//				controller.process( request, response );
//			} catch (UserNotLoggedException e) {
//				RequestDispatcher loginDispatcher = request.getRequestDispatcher(ControlMapper.HOME.getAction());
//				loginDispatcher.forward(request, response);
//				return;
//			}
//		}
//		RequestDispatcher dispatcher = request.getRequestDispatcher(controller.getCurrentControllerTarget());
//		if(!response.isCommitted())
//			dispatcher.forward( request, response );
//	}
//
//    @SuppressWarnings("unused")
//	private String getTarget(HttpServletRequest request) {
//    	final String[] uriTable = 	request.getRequestURI().split("/");
//    	if(uriTable != null && uriTable.length>0 ){
//    		return uriTable[uriTable.length-1];
//    	}
//    	return null;
//    }
//    
//    private void setRequestEncoding(HttpServletRequest request) {
//		try {
//			request.setCharacterEncoding("utf-8");
//		} catch (UnsupportedEncodingException e) {
//			log.error("UnsuppoetedEncodingException", e);
//		}
//	}
//}
