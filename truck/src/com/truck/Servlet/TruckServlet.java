package com.truck.Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.truck.Dao.CarDao;
import com.truck.entity.Car;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TruckServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6261777200708053455L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		   resp.setHeader("Content-type", "text/html;charset=UTF-8");  
		JSONArray jsonArray = new JSONArray();
		JSONObject json=new  JSONObject();
		CarDao truckDao=new CarDao();
		String action = req.getParameter("action");
		if(action.equals("add")){
			Car car = new Car();
			car.setHeight(req.getParameter("height"));
			car.setWeight(req.getParameter("weight"));
			car.setWidth(req.getParameter("width"));
			car.setTruckbirth(req.getParameter("truckbirth"));
			car.setVariety(req.getParameter("variety"));
			car.setLength(req.getParameter("length"));
			car.setTruckcardnumber(req.getParameter("truckcardnumber"));
			int addTruck = truckDao.addTruck(car);
			if(addTruck>=0){
				boolean addCarUserRelation = truckDao.addCarUserRelation(req.getParameter("userId"), addTruck+"");
				if(addCarUserRelation){
					json.put("addtruckstate", true);
				}else{
					json.put("addtruckstate", false);
				}
			}else{
				json.put("addtruckstate", false);
			}
		}else if(action.equals("delete")){
		}else if(action.equals("queryAllTrucks")){
			String userid = req.getParameter("userid");
			List<Car> trucks = truckDao.queryAllCarByUserid(userid);
			for(Car truck:trucks){
				JSONObject jsonTruck = JSONObject.fromObject(truck);
				jsonArray.add(jsonTruck);
				
			}
			ServletOutputStream outputStream = resp.getOutputStream();
			outputStream.write(jsonArray.toString().getBytes("utf-8"));
			outputStream.flush();
			outputStream.close();
			return;
		}
		ServletOutputStream outputStream = resp.getOutputStream();
		outputStream.print(json.toString());
		outputStream.flush();
		outputStream.close();
	}

}
