package com.truck.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.truck.Util.JDBCUtil;
import com.truck.entity.Car;
import com.truck.entity.CarSource;
import com.truck.entity.User;

public class CarSourceDao {
	public int addTruckSource(CarSource Carsource) {
		ResultSet rs = null;
		int executeUpdate = -1;
		PreparedStatement prestat = null;
		Connection con = JDBCUtil.getCon();
		String sql = "insert  into tb_truck_source( load_date,start_place,stop_place,start_place_point,introcduce,publish_date,state,truck_id) values(?,?,?,?,?,?,?,?)";
		try {
			prestat = con.prepareStatement(sql);
			prestat.setString(1, Carsource.getLoad_date());
			prestat.setString(2, Carsource.getStart_place());
			prestat.setString(3, Carsource.getStop_place());
			prestat.setString(4, Carsource.getStart_place_point());
			prestat.setString(5, Carsource.getIntrocduce());
			prestat.setString(6, System.currentTimeMillis() + "");
			prestat.setString(7, Carsource.getState());
			prestat.setString(8, Carsource.getTruck_id());
			executeUpdate = prestat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} finally {
			JDBCUtil.close(con, prestat, rs);
		}
		return queryLastCarSourceId();
	}

	private int queryLastCarSourceId() {
		int id = -1;
		ResultSet res = null;
		PreparedStatement prestat = null;
		Connection connection = JDBCUtil.getCon();
		String sql = "select * from tb_truck_source  ";
		try {
			prestat = connection.prepareStatement(sql);
			res = prestat.executeQuery();
			while (res.next()) {
			} 
			res.last();
			id = res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(connection, prestat, res);
		}
		return id;
	}

	public boolean addCarSourceUserRelation(String userId, String CarSourceId) {
		ResultSet rs = null;
		int executeUpdate = -1;
		PreparedStatement prestat = null;
		Connection connect = JDBCUtil.getCon();
		String sql = "insert  into tb_user_trucksource(userid,trucksourceid) values(?,?)";
		try {
			prestat = connect.prepareStatement(sql);
			prestat.setString(1, userId);
			prestat.setString(2, CarSourceId);
			executeUpdate = prestat.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.close(connect, prestat, rs);
		}
		return executeUpdate > 0 ? true : false;
	}

	public List<CarSource> queryAllCarSourceByUserid(String userid) {
		ResultSet rs = null;
		ArrayList<CarSource> carSources = new ArrayList<CarSource>();
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		String sql = "select * from tb_user_trucksource where userid=?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, userid);
			rs = pstm.executeQuery();
			while (rs.next()) {
				String truckSourceid = rs.getString(2);
				CarSource truckSrouce = queryByCarSourceid(truckSourceid);
				carSources.add(truckSrouce);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return carSources;
	}

	public List<CarSource> queryAllCarSourceByCondition(String startplace, String stopplace, String starttime) {
		CarDao truckDao = new CarDao();
		UserDao userDao=new UserDao();
		ResultSet rs = null;
		CarSource truckSource = null;
		ArrayList<CarSource> truckSources = new ArrayList<CarSource>();
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		String sql = "select * from tb_truck_source where start_place like ? and stop_place like ? and load_date like ? order by publish_date desc";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, startplace);
			pstm.setString(2, stopplace);
			pstm.setString(3, starttime);
			rs = pstm.executeQuery();
			while (rs.next()) {
				truckSource = new CarSource();
				truckSource.setLoad_date(rs.getString(2));
				truckSource.setStart_place(rs.getString(3));
				truckSource.setStop_place(rs.getString(4));
				truckSource.setStart_place_point(rs.getString(5));
				truckSource.setIntrocduce(rs.getString(6));
				truckSource.setPublish_date(rs.getString(7));
				truckSource.setState(rs.getString(8));
				truckSource.setTruck(truckDao.queryByCarid(rs.getString(9)));
				//通过trucksourceid 查询userid
				String turcksourceid = rs.getString(1);
				String userId = queryUserIdByCarSourceId(turcksourceid);
				User user = userDao.queryUserById(userId);
				truckSource.setUser(user);
				truckSources.add(truckSource);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return truckSources;
	}
	
	public CarSource queryByCarSourceid(String truckSourceid) {
		UserDao userDao=new UserDao();
		ResultSet rs = null;
		CarSource truckSource = null;
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		String sql = "select * from tb_trucksource where _id=?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1, truckSourceid);
			rs = pstm.executeQuery();
			while (rs.next()) {
				truckSource = new CarSource();
				truckSource.setLoad_date(rs.getString(2));
				truckSource.setStart_place(rs.getString(3));
				truckSource.setStop_place(rs.getString(4));
				truckSource.setStart_place_point(rs.getString(5));
				truckSource.setIntrocduce(rs.getString(6));
				truckSource.setPublish_date(rs.getString(7));
				truckSource.setState(rs.getString(8));
				//通过trucksourceid 查询userid
				String turcksourceid = rs.getString(1);
				String userId = queryUserIdByCarSourceId(truckSourceid);
				User user = userDao.queryUserById(userId);
				truckSource.setUser(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return truckSource;
	}



	public String queryUserIdByCarSourceId(String carId){
		ResultSet rs = null;
		PreparedStatement prestat = null;
		Connection con = JDBCUtil.getCon();
		String userId="-1";
		String sql = "select * from tb_user_trucksource where trucksourceid = ? ";
		try {
			prestat = con.prepareStatement(sql);
			prestat.setString(1, carId);
			rs = prestat.executeQuery();
			while (rs.next()) {
				userId=rs.getString(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, prestat, rs);
		}
		return userId;
	}
	
}
