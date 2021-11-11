package global.module;

import global.bean.DatabaseBean;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * 데이터베이스 모듈 클래스
 */
public class DatabaseModule
{
	private static final String CLASS;
	private static final String URL;
	private static final String ID;
	private static final String PASSWORD;
	
	static
	{
		DatabaseBean databaseBean = getDatabaseBean();
		
		CLASS = databaseBean.getClassName();
		URL = databaseBean.getUrl();
		ID = databaseBean.getId();
		PASSWORD = databaseBean.getPassword();
	}
	
	/**
	 * 데이터베이스 객체 반환 메서드
	 *
	 * @return [DatabaseBean] 데이터베이스 객체
	 */
	private static DatabaseBean getDatabaseBean()
	{
		DatabaseBean databaseBean;
		
		// 데이터베이스 객체 반환 시도
		try
		{
			HashMap<String, String> map = Util.getProperties("database");
			
			databaseBean = new DatabaseBean(map.get("class"), map.get("url"), map.get("id"), map.get("password"));
		}
		
		// 데이터 입출력 예외
		catch (IOException e)
		{
			e.printStackTrace();
			
			databaseBean = null;
		}
		
		return databaseBean;
	}
	
	/**
	 * Connection 객체 반환 메서드
	 *
	 * @return [Connection] Connection 객체
	 *
	 * @throws ClassNotFoundException 찾을 수 없는 클래스 예외
	 * @throws SQLException SQL 예외
	 */
	public Connection getConnection() throws ClassNotFoundException, SQLException
	{
		Class.forName(CLASS);
		
		return DriverManager.getConnection(URL, ID, PASSWORD);
	}
	
	/**
	 * 연결 해제 메서드
	 *
	 * @param connection: [Connection] Connection 객체
	 */
	public void close(Connection connection)
	{
		// 연결 해제 시도
		try
		{
			// Connection 객체가 유효할 경우
			if (connection != null)
			{
				connection.close();
			}
		}
		
		// SQL 예외
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 연결 해제 메서드
	 *
	 * @param statement: [PreparedStatement] PreparedStatement 객체
	 */
	public void close(PreparedStatement statement)
	{
		// 연결 해제 시도
		try
		{
			// PreparedStatement 객체가 유효할 경우
			if (statement != null)
			{
				statement.close();
			}
		}
		
		// SQL 예외
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 연결 해제 메서드
	 *
	 * @param resultSet: [ResultSet] ResultSet 객체
	 */
	public void close(ResultSet resultSet)
	{
		// 연결 해제 시도
		try
		{
			// ResultSet 객체가 유효할 경우
			if (resultSet != null)
			{
				resultSet.close();
			}
		}
		
		// SQL 예외
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
}