package com.truck.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.truck.Util.JDBCUtil;
import com.truck.entity.User;

import net.sf.json.JSONObject;

public class IMEIDao {

	/**
	 * 
	 * 
	 * @param phone
	 * @param password
	 * @return
	 */
	public boolean select(String phone, String imei) {
		PreparedStatement prestat = null;
		ResultSet res = null;
		boolean next = false;
		Connection con = JDBCUtil.getCon();
		String sql = "select * from tb_imei where phonenumber=? and imei=?";
		try {
			prestat = con.prepareStatement(sql);
			prestat.setString(1, phone);
			prestat.setString(2, imei);
			res = prestat.executeQuery();
			next = res.next();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, prestat, res);
		}
		return next;
	}
	/**
	 * 
	 * 
	 * @param phone
	 * @return
	 */
	public boolean delete_IMEI(String phone) {
		ResultSet res = null;
		int i = -1;
		PreparedStatement prestat = null;
		Connection con = JDBCUtil.getCon();
		String sql = "update tb_imei set imei=-1 where phonenumber =?";
		try {
			prestat = con.prepareStatement(sql);
			prestat.setString(1, phone);
			i = prestat.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCUtil.close(con, prestat, res);
		}
		return i > 0;
	}
	/**
	 * 
	 * 
	 * @param phone
	 * @param IMEI
	 * @return
	 */
	public boolean editIMEI(String phone, String IMEI) {
		PreparedStatement prestat = null;
		ResultSet re = null;
		boolean next = false;
		int i = -1;
		boolean result = false;
		Connection con = JDBCUtil.getCon();
		String sql = "insert  into tb_imei(phonenumber,IMEI) values(?,?)";
		try {
			prestat = con.prepareStatement(sql);
			prestat.setString(1, phone);
			prestat.setString(2, IMEI);
			i = prestat.executeUpdate();
			if (i >= 0) {
				result = true;
			} else {
				result = false;
			}
		} catch (SQLException e) {
			sql = "update tb_imei set imei=? where phonenumber=?";
			try {
				prestat = con.prepareStatement(sql);
				prestat.setString(1, IMEI);
				prestat.setString(2, phone);
				i = prestat.executeUpdate();
				if (i >= 0) {
					// 更新成功 return true；
					result = true;
				} else {
					result = false;
				}
			} catch (SQLException e1) {
				e.printStackTrace();
			}
		} finally {
			JDBCUtil.close(con, prestat, re);
		}
		return result;
	}


}
