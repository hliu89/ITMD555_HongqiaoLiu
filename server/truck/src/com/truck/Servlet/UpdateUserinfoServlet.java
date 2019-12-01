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

public class UpdateUserinfoServlet extends HttpServlet {
	// private UserDao userDao;

	/**
	 * 
	 */
	private static final long serialVersionUID = -5369729728362988073L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDao ud = new UserDao();
		JSONObject json = new JSONObject();
		String phoneNumber = (String) request.getParameter("phoneNumber");
		String username = (String) request.getParameter("username");
		String headimg = (String) request.getParameter("headimg");
		String area = (String) request.getParameter("area");
		String introduce = (String) request.getParameter("introduce");
		User user = new User();
		user.setPhoneNumber(phoneNumber);
		user.setUsername(username);
		user.setAvatarurl(headimg);
		user.setCity(area);
		user.setIntroduce(introduce);
		boolean isSuccess = ud.updataUserInfo(user);
		if (isSuccess) {
			json.put("state", true);
		} else {
			json.put("state", false);
		}
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.print(json.toString());
		outputStream.flush();
		outputStream.close();
		return;
	}
}
