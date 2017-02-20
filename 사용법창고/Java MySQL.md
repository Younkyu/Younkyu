###**Java MySQL**

 먼저 레퍼런스 라이브러리에 connection-j(자바) 추가

**예제**

	package com.leeyounkyu;
	
	import java.sql.Connection;
	import java.sql.DriverManager;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.sql.Statement;
	
	public class DatabaseMain {
		
		public static void main(String[] args) {
			
	//		create("dd","dddd");
	//		readAll();
	//		read(9);
	//		update(9, "asd", "asdfds");
	//		read(9);
	//		readAll();
	//		delete(9);
			readAll();
			deleteRange(3,5);
			readAll();
		}
		
		public static void deleteRange(int from, int to) {
			try(Connection conn = connect();) {
				String sql = "delete from bbs2 where bbsno >= ? and bbsno <= ?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, from);
				pstmt.setInt(2, to);
				pstmt.executeUpdate();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public static void delete(int bbsno) {
			try(Connection conn = connect();) {
				String sql = "delete from bbs2 where bbsno = ?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bbsno);
				pstmt.executeUpdate();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		public static void update(int bbsno, String title, String content) {
			try(Connection conn = connect();) {
				String sql = "update bbs2 set title = ? , content = ? , nDate = now() where bbsno = ?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, content);
				pstmt.setInt(3, bbsno);
				
				pstmt.executeUpdate();
				
				
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		public static void read(int bbsno) {
			try(Connection conn = connect();) {
				String sql = "select * from bbs2 where bbsno = ?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bbsno);
				ResultSet rs = pstmt.executeQuery();
				
				if(rs.next()) {
					String title = rs.getString("title");
					String content = rs.getString("content");
					
					System.out.printf("title:%s, content:%s,\n", title,content);
				}
				
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		public static void readAll() {
			//try - with을 통한 close 처리
					try(Connection conn = connect();){
				
						// 1. 쿼리 작성
						String sql = "select * from bbs2;";
						
						
						// 2. 데이터베이스에 쓰는 도구
						Statement stmt = conn.createStatement();
						// 3. 쿼리 ㅅㄹ행후 결과를 받은 것
						ResultSet rs = stmt.executeQuery(sql);
						// 4. 변수에 담긴 결과셋을 반복문을 돌면서 화면에 추력
						while(rs.next()) {
							String title = rs.getString("title");
							String content = rs.getString("content");
							
							System.out.printf("title:%s, content:%s,\n", title,content);
						}
						
						// 4. 도구를 이용해서 쿼리 실행
	//					stmt.execute(sql);
						
						
						
					} catch(Exception e) {
						e.printStackTrace();
					} 
	//				finally {
	//					// 5. 데이터베이스 닫기
	//					try {
	//						conn.close();
	//					} catch (SQLException e) {
	//						// TODO Auto-generated catch block
	//						e.printStackTrace();
	//					}
	//				}
		}
		
		public static void create(String title, String content) {
		
			//try - with을 통한 close 처리
			try(Connection conn = connect();){
		
			
				// 2. 쿼리 작성
				String sql = "insert into bbs2(title,content,nDate) values(?,?,now());";
	//
	//			// 3. 데이터베이스에 쓰는 도구
	//			Statement stmt = conn.createStatement();
				
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, content);
				pstmt.execute();
				
				pstmt.setString(1, title);
				pstmt.setString(2, content);
				pstmt.execute();
				
				pstmt.setString(1, title);
				pstmt.setString(2, content);
				pstmt.execute();
				
				pstmt.setString(1, title);
				pstmt.setString(2, content);
				pstmt.execute();
	//			// 4. 도구를 이용해서 쿼리 실행
	//			stmt.execute(sql);
				
				
				
			} catch(Exception e) {
				e.printStackTrace();
			} 
	//		finally {
	//			// 5. 데이터베이스 닫기
	//			try {
	//				conn.close();
	//			} catch (SQLException e) {
	//				// TODO Auto-generated catch block
	//				e.printStackTrace();
	//			}
	//		}
			
		}
		
		public static Connection connect() {
			Connection conn = null;
			try {
				String id = "root";
				String pw = "1324";
				String dbName = "yk";
				
				String url = "jdbc:mysql://localhost:3306/" + dbName;
				
				Class.forName("com.mysql.jdbc.Driver");
				// 클래스를 동적으로 로드하는 방법
				
				conn=DriverManager.getConnection(url,id,pw);
				System.out.println("Database " + dbName+"에 연결되었습니다.");
				
				return conn;
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	
	}
