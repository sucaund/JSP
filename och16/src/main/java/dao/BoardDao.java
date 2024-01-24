package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDao {
	
	private static BoardDao instance;
	private BoardDao() {
	}
	public  static BoardDao getInstance() {   //싱글톤으로 인한 메모리 관리
		if(instance == null) {
			instance = new BoardDao();
		}
		
		return instance;
	}
	
	private Connection getConnection()  {    //DBCP
		Connection conn = null;
		
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/OracleDB");
			conn = ds.getConnection();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println(e.getMessage());
		}
		
		return conn;
		}
		
	//게시글 개수 새는 메소드
	public int getTotalCnt() throws SQLException {
		Connection conn = null;
		String sql = "select count(*) from board ";
		Statement stmt = null;
		ResultSet rs = null;
		int tot =0 ;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				tot = rs.getInt(1); // sql 실행문의 첫번쨰 결과값만
				System.out.println(tot);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (stmt != null)
				stmt.close();
			if (conn != null)
				conn.close();
		}
		return tot;
	};
		
	

}
