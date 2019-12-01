package com.truck.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.truck.Dao.IMEIDao;

public class LogoutServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5794513261573506113L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		IMEIDao imerDao = new IMEIDao();
		String phoneNumber = (String) req.getParameter("phoneNumber");
		JSONObject json = new JSONObject();
		boolean deleteIMEI = imerDao.delete_IMEI(phoneNumber);
		if (deleteIMEI) {
			json.put("logout", 1);
		} else {
			json.put("logout", 0);
		}
		ServletOutputStream outputStream = resp.getOutputStream();
		outputStream.print(json.toString());
		outputStream.flush();
		outputStream.close();
		return;
	}

}