package com.truck.Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.truck.Dao.UserDao;
import com.truck.entity.User;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 3916329794101592961L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		UserDao ud = new UserDao();
		String phoneNumber = (String) req.getParameter("phoneNumber");
		String password = (String) req.getParameter("password");
		JSONObject json;
		json = (JSONObject) req.getAttribute("json");
		if (json == null) {
			json = new JSONObject();
		}

		User user = (User) ud.Select(phoneNumber, password);
		if (user != null) {
			json.put("loginStateCode", 1);
			json.put("leanCloudId", user.getLeanCloudId() + "");
			req.setAttribute("json", json);
			req.getRequestDispatcher("AutoLoginServlet").forward(req, resp);

		} else {
			json.put("loginStateCode", 0);
			ServletOutputStream outputStream = resp.getOutputStream();
			outputStream.print(json.toString());
			outputStream.flush();
			outputStream.close();
			return;
		}

	}

}