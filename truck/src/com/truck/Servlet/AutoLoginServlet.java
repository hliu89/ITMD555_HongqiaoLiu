package com.truck.Servlet;


import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.truck.Dao.IMEIDao;

import net.sf.json.JSONObject;

public class AutoLoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7944374471353439418L;


	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}


	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		IMEIDao objIMEIDao = new IMEIDao();
		String phoneNumber = (String) request.getParameter("phoneNumber");
		String imei = (String) request.getParameter("IMEI");
		System.out.println(imei);
		JSONObject json;
		json = (JSONObject) request.getAttribute("json");
		if (json == null) {
			json = new JSONObject();
			boolean query = objIMEIDao.select(phoneNumber, imei);
			json.put("isLogin", query);
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.print(json.toString());
			outputStream.flush();
			outputStream.close();
		} else {
			boolean updateIMEI = objIMEIDao.editIMEI(phoneNumber, imei);
			json.put("addIMEIStateCode", updateIMEI?1:0);
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.print(json.toString());
			outputStream.flush();
			outputStream.close();
			
		}
	}
}
