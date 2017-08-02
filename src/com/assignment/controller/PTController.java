package com.assignment.controller;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.assignment.util.PublicTransportWS;



public class PTController extends HttpServlet { 

	private static final long serialVersionUID = 1L;
	Logger log = null;
	Map<String,Object> serviceMap;


	public void init(ServletConfig config) throws ServletException {
		log = Logger.getLogger(this.getClass().getName());
		super.init(config);
		serviceMap = new Hashtable<String, Object>();
		try{
		     serviceMap.put("publicTransportWS", new PublicTransportWS());
		   }catch(Exception e){
			   log.error("Error-init{Class not found} :"+e);
		   }
	}  

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(log.isInfoEnabled())log.info("{REQUEST}{doGet}{NA}");
		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String reqType = request.getParameter("reqType");
		try{
			if(reqType != null && !reqType.equals("")) {
				if (reqType.indexOf("jsp") != -1) {
					RequestDispatcher rd = request.getRequestDispatcher(reqType);
					rd.forward(request, response);
				} else {
					ActionExecute serviceObj = (ActionExecute) serviceMap.get(reqType);
					String returnService = serviceObj.processRequest(request, response);
					System.out.println("Return Service :"+returnService);
					if(returnService.indexOf("jsp") != -1) {
						RequestDispatcher rd = request.getRequestDispatcher(returnService);
						rd.forward(request, response);
					}else{
						ActionExecute serviceObj2 = (ActionExecute) serviceMap.get(returnService);
						if (serviceObj2 != null) {
							String returnService2 = serviceObj2.processRequest(request, response);
							RequestDispatcher rd = request.getRequestDispatcher(returnService2);
							rd.forward(request, response);
						}
					}
				}

			}else{
				RequestDispatcher rd = request.getRequestDispatcher("/jsp/index.jsp");
				rd.forward(request, response);
			}




		}catch(IllegalStateException e){
			log.error("Error-doPost :", e);
		}catch(Exception e){
			log.error("Error-doPost :", e);
		}
	}

}
