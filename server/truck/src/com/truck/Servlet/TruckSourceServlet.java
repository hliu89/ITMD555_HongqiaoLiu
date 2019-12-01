package com.truck.Servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.truck.Dao.CarSourceDao;
import com.truck.entity.CarSource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
public class TruckSourceServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1399259985291476579L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		JSONArray jsonArray = new JSONArray();
		JSONObject json = new JSONObject();
		CarSourceDao truckSourceDao = new CarSourceDao();
		String action = req.getParameter("action");
		if (action.equals("add")) {
			CarSource truckSource = new CarSource();
			truckSource.setLoad_date(req.getParameter("load_date"));
			truckSource.setStart_place(req.getParameter("start_place"));
			truckSource.setStop_place(req.getParameter("stop_place"));
			truckSource.setStart_place_point(req.getParameter("start_place_point"));
			truckSource.setIntrocduce(req.getParameter("introcduce"));
			truckSource.setState(req.getParameter("state"));
			truckSource.setTruck_id(req.getParameter("truck_id"));

			int addTruckSource = truckSourceDao.addTruckSource(truckSource);
			if (addTruckSource >= 0) {
				boolean addTruckSourceUserRelation = truckSourceDao.addCarSourceUserRelation(req.getParameter("userid"), addTruckSource+"");
				if (addTruckSourceUserRelation) {
					json.put("addtrucksourcestate", true);
				} else {
					json.put("addtrucksourcestate", false);
				}
			} else {
				json.put("addtrucksourcestate", false);
			}
		} else if (action.equals("delete")) {
		} else if (action.equals("queryMyAllTruckSources")) {
			String userid = req.getParameter("userid");
			List<CarSource> truckSources = truckSourceDao.queryAllCarSourceByUserid(userid);
			for (CarSource truckSource : truckSources) {
				JSONObject jsonTruckSource = JSONObject.fromObject(truckSource);
				jsonArray.add(jsonTruckSource);
			}
			ServletOutputStream outputStream = resp.getOutputStream();
			outputStream.write(jsonArray.toString().getBytes("utf-8"));
			outputStream.flush();
			outputStream.close();
			return;
		} else if(action.equals("queryAllTruckSources")){
			String startplace = req.getParameter("startplace");
			String stopplace = 	req.getParameter("stopplace");
			String starttime = 	req.getParameter("starttime");
			List<CarSource> truckSources =  truckSourceDao.queryAllCarSourceByCondition(startplace,stopplace,starttime);
			for (CarSource truckSource : truckSources) {
				JSONObject jsonTruckSource = JSONObject.fromObject(truckSource);
				jsonArray.add(jsonTruckSource);
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
