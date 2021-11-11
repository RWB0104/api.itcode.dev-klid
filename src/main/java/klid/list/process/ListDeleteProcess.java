package klid.list.process;

import global.bean.ResponseBean;
import global.module.DatabaseModule;
import global.module.Process;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * 리스트 DELETE 프로세스 클래스
 *
 * @author RWB
 * @since 2021.11.01 Mon 22:49:56
 */
public class ListDeleteProcess extends Process
{
	/**
	 * 생성자 메서드
	 *
	 * @param request: [HttpServletRequest] HttpServletRequest 객체
	 * @param response: [HttpServletResponse] HttpServletResponse 객체
	 */
	public ListDeleteProcess(HttpServletRequest request, HttpServletResponse response)
	{
		super(request, response);
	}
	
	/**
	 * 리스트 아이템 삭제 응답 반환 메서드
	 *
	 * @param seq: [long] 시퀀스
	 *
	 * @return [Response] 응답 객체
	 */
	public Response deleteListItemResponse(long seq)
	{
		Response response;
		
		ResponseBean<String> responseBean = new ResponseBean<>();
		
		DatabaseModule databaseModule = new DatabaseModule();
		Connection connection = null;
		PreparedStatement statement = null;
		
		// 리스트 아이템 삭제 응답 반환 시도
		try
		{
			String sql = "DELETE FROM WORKLIST WHERE SEQ = ?";
			
			connection = databaseModule.getConnection();
			
			statement = connection.prepareStatement(sql);
			statement.setLong(1, seq);
			statement.executeUpdate();
			
			response = Response.noContent().build();
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
			databaseModule.close(statement);
			databaseModule.close(connection);
		}
		
		return response;
	}
}