package com.truck.Servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.truck.Util.MarkUtil;

public class UploadHeadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1582773368734841455L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String imgName=null ;

		String realPath = request.getRealPath("");
		String phoneNumber = (String) request.getParameter("userphonenumber");
		String imgcode = (String) request.getParameter("imgcode");
		if (imgcode == null) {
			imgName="";
		} else {
			byte[] decode = MarkUtil.decode(imgcode);
			for (int i = 0; i < decode.length; ++i) {
				if (decode[i] < 0) {
					decode[i] += 256;
				}
			}
			String imgFilePath = realPath + "imghead/";
			File file = new File(imgFilePath);
			if(!file.exists()){
				file.mkdirs();
			}
			File file2 = new File(file,phoneNumber + ".png" );
			if(!file2.exists()){
				file2.createNewFile();
			}
			FileOutputStream out = new FileOutputStream(file2);
			out.write(decode);
			out.flush();
			out.close();
			imgName=phoneNumber + ".png";
		}
		ServletOutputStream outputStream = response.getOutputStream();
		outputStream.print(imgName);
		outputStream.flush();
		outputStream.close();
	}

}
