package com.truck.Servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import com.truck.Dao.UserDao;

public class RigisterServlet extends HttpServlet {
	// private UserDao userDao;

	/**
	 * 
	 */
	private static final long serialVersionUID = -3183746605742690802L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDao ud = new UserDao();
		JSONObject json = new JSONObject();

		String phoneNumber = (String) request.getParameter("phoneNumber");
		String password = (String) request.getParameter("password");
		int insertCode = ud.addUser(phoneNumber, password);

		if (insertCode < 0) {
			json.put("registerStateCode", 0);
			ServletOutputStream outputStream = response.getOutputStream();
			outputStream.print(json.toString());
			outputStream.flush();
			outputStream.close();
			return;
		} else {
			json.put("registerStateCode", 1);

			request.setAttribute("json", json);
			request.getRequestDispatcher("LoginServlet").forward(request, response);
		}
	}
}
