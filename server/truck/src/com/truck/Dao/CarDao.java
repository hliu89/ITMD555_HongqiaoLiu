package com.truck.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.truck.Util.JDBCUtil;
import com.truck.entity.Car;
import com.truck.entity.User;

public class CarDao {
	public int addTruck(Car car) {
		ResultSet rs = null;
		int exeup = -1;
		PreparedStatement prestat = null;
		Connection connect = JDBCUtil.getCon();
		String sql = "insert  into tb_truck(truckbirth,length,width,height,weight,variety,truckcardnumber) values(?,?,?,?,?,?,?)";
		try {
			prestat = connect.prepareStatement(sql);
			prestat.setString(1, car.getTruckbirth());
			prestat.setString(2, car.getLength());
			prestat.setString(3, car.getWidth());
			prestat.setString(4, car.getHeight());
			prestat.setString(5, car.getWeight());
			prestat.setString(6, car.getVariety());
			prestat.setString(7, car.getTruckcardnumber());
			exeup = prestat.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} finally {
			JDBCUtil.close(connect, prestat, rs);
		}
		return queryLastCarId();
	}

	private int queryLastCarId() {
		int id = -1;
		ResultSet res = null;
		PreparedStatement prestat = null;
		Connection connect = JDBCUtil.getCon();
		User user = null;
		String sql = "select * from tb_truck  ";
		try {
			prestat = connect.prepareStatement(sql);
			res = prestat.executeQuery();
			while (res.next()) {
			} 
			res.last();
			id = res.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(connect, prestat, res);
		}
		return id;
	}

	public boolean addCarUserRelation(String userId,String CarId){
		ResultSet rs = null;
		int executeUpdate = -1;
		PreparedStatement prestat = null;
		Connection con = JDBCUtil.getCon();
		String sql = "insert  into tb_user_truck(userid,turckid) values(?,?)";
		try {
			prestat = con.prepareStatement(sql);
			prestat.setString(1,userId);
			prestat.setString(2, CarId);
			executeUpdate = prestat.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} finally {
			JDBCUtil.close(con, prestat, rs);
		}
		return executeUpdate>0?true:false;
	}
	public Car queryByCarid(String Car){
		ResultSet rs = null;
		Car car=null;
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		String sql = "select * from tb_truck where _id=?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1,Car);
			rs = pstm.executeQuery();
			while(rs.next()){
				car = new Car();
				car.setId(rs.getString(1));
				car.setTruckbirth(rs.getString(2));
				car.setLength(rs.getString(3));
				car.setWidth(rs.getString(4));
				car.setHeight(rs.getString(5));
				car.setWeight(rs.getString(6));
				car.setVariety(rs.getString(7));
				car.setTruckcardnumber(rs.getString(8));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return car;
	}
	
	public List<Car> queryAllCarByUserid(String userid){
		ResultSet rs = null;
		ArrayList<Car> trucks = new ArrayList<Car>();
		PreparedStatement pstm = null;
		Connection con = JDBCUtil.getCon();
		String sql = "select * from tb_user_truck where userid=?";
		try {
			pstm = con.prepareStatement(sql);
			pstm.setString(1,userid);
			rs = pstm.executeQuery();
			while(rs.next()){
				String truckid = rs.getString(2);
				Car truck = queryByCarid(truckid);
				trucks.add(truck);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, pstm, rs);
		}
		return trucks;
	}
	
}
