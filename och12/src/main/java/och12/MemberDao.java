package och12;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import jakarta.security.auth.message.callback.SecretKeyCallback.Request;

//singleton + DBCP  -> Memory 절감 + DOS공격 
public class MemberDao {
	private static MemberDao instance;

	private MemberDao() {
	}

	public static MemberDao getInstance() {
		if (instance == null) {
			instance = new MemberDao();
		}

		return instance;
	}

	private Connection getConnection() {
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

	// PreparedStatement
	public int check(String id, String passwd) throws SQLException {

		int result = 0;
		Connection conn = null;
		String sql = "select passwd from member2 where id=?";
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			// id OK
			if (rs.next()) {
				String dbPasswd = rs.getString(1); // sql 실행문의 첫번쨰 결과값만
				if (dbPasswd.equals(passwd))
					result = 1;
				else
					result = 0;
			} else
				result = -1;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return result;
	}

	public int insert(Member2 member) throws SQLException {
		int result = 0;
		Connection conn = null;
		String sql = "insert into member2 values(?,?,?,?,?,sysdate)";
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPasswd());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getAddress());
			pstmt.setString(5, member.getTel());
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return result;
	} // insert

	
	  public List<Member2> list() throws SQLException { 
	      Member2 result = null;
		  Connection conn = null; 
		  String sql = "select * from member2"; 
		  PreparedStatement pstmt = null; 
		  ResultSet rs = null;
		  ArrayList<Member2> m2 = new ArrayList<Member2>(); 
		  
		  try {			
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			ArrayList<Member2> member = new ArrayList<Member2>(); 
			if(rs.next()) {
				result.setPasswd(rs.getString("passwd"));
				result.setName(rs.getString("name"));
				result.setAddress(rs.getString("Address"));
				result.setTel(rs.getString("tel"));
				result.setReg_date(rs.getDate("Reg_date"));
				m2.add(result);
			}
			}
				
				catch (Exception e) { 
	// TODO: handle exception 
				} finally {
					if (pstmt != null)
						pstmt.close();
					if (conn != null)
						conn.close();
				} return m2; 
			}//list 
	  
	  
	  public Member2 select(String id) throws SQLException {
      Member2 result = null;
	  Connection conn = null; 
	  String sql = "select * from member2 where id=?"; 
	  PreparedStatement pstmt = null; 
	  ResultSet rs = null;
	  try {			
		conn = getConnection();
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, id);
		rs = pstmt.executeQuery();
		
		if(rs.next()) {
			String passwd = rs.getString("passwd");
			String name = rs.getString("name");
			String address = rs.getString("address");
			String tel = rs.getString("tel");
			Date reg_date = rs.getDate("reg_date");
			
			Member2 member2= new Member2();
			member2.setPasswd(passwd);
			member2.setName(name);
			member2.setAddress(address);
			member2.setTel(tel);
			member2.setReg_date(reg_date);
			result = member2;
		}
	  
	  } catch (Exception e) { 
		// TODO: handle exception 
	  } finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
	  } 
	  return result; 
	  }//select
	 
	public int delete(String passwd) throws SQLException {
		int result = 0;
		Connection conn = null;
		String sql = "DELETE FROM member2 WHERE passwd = ?";
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, passwd);

			result = pstmt.executeUpdate();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return result;

	}

}
