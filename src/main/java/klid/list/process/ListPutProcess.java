package klid.list.process;

import com.fasterxml.jackson.databind.ObjectMapper;
import global.bean.ResponseBean;
import global.module.DatabaseModule;
import global.module.Process;
import global.module.Util;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import klid.list.bean.WorkListBean;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * 리스트 PUT 프로세스 클래스
 *
 * @author RWB
 * @since 2021.11.01 Mon 22:35:45
 */
public class ListPutProcess extends Process
{
	/**
	 * 생성자 메서드
	 *
	 * @param request: [HttpServletRequest] HttpServletRequest 객체
	 * @param response: [HttpServletResponse] HttpServletResponse 객체
	 */
	public ListPutProcess(HttpServletRequest request, HttpServletResponse response)
	{
		super(request, response);
	}
	
	/**
	 * 리스트 아이템 갱신 응답 반환 메서드
	 *
	 * @param seq: [long] 시퀀스
	 * @param workListBean: [WorkListBean] WORKLIST 테이블 객체
	 *
	 * @return [Response] 응답 객체
	 */
	public Response putListItemResponse(long seq, WorkListBean workListBean)
	{
		Response response;
		
		ResponseBean<String> responseBean = new ResponseBean<>();
		
		DatabaseModule databaseModule = new DatabaseModule();
		Connection connection = null;
		PreparedStatement statement = null;
		
		// 리스트 아이템 갱신 응답 반환 시도
		try
		{
			workListBean.setSeq(seq);
			
			String sql = "UPDATE WORKLIST SET TYPE = ?, WORKER = ?, DESCRIPTION = ?, START = ?, END = ? WHERE SEQ = ?";
			
			connection = databaseModule.getConnection();
			
			statement = connection.prepareStatement(sql);
			statement.setString(1, workListBean.getType());
			statement.setString(2, workListBean.getWorker());
			statement.setString(3, workListBean.getDescription());
			statement.setDate(4, workListBean.getStart());
			statement.setDate(5, workListBean.getEnd());
			statement.setLong(6, workListBean.getSeq());
			statement.executeUpdate();
			
			responseBean.setFlag(true);
			responseBean.setTitle("success");
			responseBean.setMessage(Util.builder(seq, " item updated"));
			responseBean.setBody(new ObjectMapper().writeValueAsString(workListBean));
			
			response = Response.ok(responseBean, MediaType.APPLICATION_JSON).build();
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