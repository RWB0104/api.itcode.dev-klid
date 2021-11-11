package klid.list.process;

import global.bean.ResponseBean;
import global.module.DatabaseModule;
import global.module.Process;
import global.module.Util;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import klid.list.bean.WorkListBean;

import java.net.URI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 리스트 POST 프로세스 클래스
 *
 * @author RWB
 * @since 2021.11.01 Mon 22:04:21
 */
public class ListPostProcess extends Process
{
	/**
	 * 생성자 메서드
	 *
	 * @param request: [HttpServletRequest] HttpServletRequest 객체
	 * @param response: [HttpServletResponse] HttpServletResponse 객체
	 */
	public ListPostProcess(HttpServletRequest request, HttpServletResponse response)
	{
		super(request, response);
	}
	
	/**
	 * 리스트 아이템 추가 응답 반환 메서드
	 *
	 * @param workListBean: [WorkListBean] WORKLIST 테이블 객체
	 *
	 * @return [Response] 응답 객체
	 */
	public Response postListItemResponse(WorkListBean workListBean)
	{
		Response response;
		
		ResponseBean<String> responseBean = new ResponseBean<>();
		
		DatabaseModule databaseModule = new DatabaseModule();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		// 리스트 추가 응답 반환 시도
		try
		{
			String sql = "INSERT INTO WORKLIST(TYPE, WORKER, DESCRIPTION, START, END) VALUES(?, ?, ?, ?, ?)";
			
			connection = databaseModule.getConnection();
			
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, workListBean.getType());
			statement.setString(2, workListBean.getWorker());
			statement.setString(3, workListBean.getDescription());
			statement.setDate(4, workListBean.getStart());
			statement.setDate(5, workListBean.getEnd());
			statement.executeUpdate();
			
			resultSet = statement.getGeneratedKeys();
			resultSet.next();
			
			response = Response.created(URI.create(Util.builder("/api/list/item/", resultSet.getLong(1)))).build();
		}
		
		// 예외
		catch (Exception e)
		{
			e.printStackTrace();
			
			responseBean.setFlag(false);
			responseBean.setTitle(e.getClass().getSimpleName());
			responseBean.setMessage(e.getMessage());
			responseBean.setBody(null);
			
			response = Response.status(Response.Status.BAD_REQUEST).entity(responseBean).type(MediaType.APPLICATION_JSON).build();
		}
		
		// 시도 후
		finally
		{
			databaseModule.close(resultSet);
			databaseModule.close(statement);
			databaseModule.close(connection);
		}
		
		return response;
	}
}