package com.meego.day23;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

public class TestPreparedStatement {
	private Connection conn;
	@Before
	public void iniConn() {
		String url = "jdbc:mysql://localhost:3306/mybase";
		String user = "root";
		String password = "root";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	
	@Test
	public void testSQLInject() throws SQLException {
		String name = "1' or 1=1 --";
		String pass = "123456";
		
		String sql = "select * from persons where name = ? and password = ?";
		PreparedStatement ppst = conn.prepareStatement(sql);
		
		ppst.setString(1, name);
		ppst.setString(2, pass);
		
		ResultSet rs = ppst.executeQuery();
		if(rs.next()) {
			int id = rs.getInt(1);
			System.out.println("登录成功");
		}
		else {
			System.out.println("用户名/密码错误,请重试!!");
		}
		rs.close();
		conn.close();
	}
}
