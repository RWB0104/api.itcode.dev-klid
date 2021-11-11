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

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.LinkedList;

/**
 * 리스트 GET 프로세스 클래스
 *
 * @author RWB
 * @since 2021.11.01 Mon 01:30:40
 */
public class ListGetProcess extends Process
{
	/**
	 * 생성자 메서드
	 *
	 * @param request: [HttpServletRequest] HttpServletRequest 객체
	 * @param response: [HttpServletResponse] HttpServletResponse 객체
	 */
	public ListGetProcess(HttpServletRequest request, HttpServletResponse response)
	{
		super(request, response);
	}
	
	/**
	 * 연별 리스트 응답 반환 메서드
	 *
	 * @param year: [int] 연
	 *
	 * @return [Response] 응답 객체
	 */
	public Response getYearListResponse(int year)
	{
		Response response;
		
		ResponseBean<LinkedList<WorkListBean>> responseBean = new ResponseBean<>();
		
		DatabaseModule databaseModule = new DatabaseModule();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		// 연별 리스트 응답 생성 시도
		try
		{
			String sql = "SELECT * FROM WORKLIST WHERE YEAR(START) = ? ORDER BY START";
			
			connection = databaseModule.getConnection();
			
			statement = connection.prepareStatement(sql);
			statement.setInt(1, year);
			
			resultSet = statement.executeQuery();
			
			LinkedList<WorkListBean> list = new LinkedList<>();
			
			while (resultSet.next())
			{
				WorkListBean workListBean = new WorkListBean();
				workListBean.setSeq(resultSet.getLong("SEQ"));
				workListBean.setType(resultSet.getString("TYPE"));
				workListBean.setWorker(resultSet.getString("WORKER"));
				workListBean.setDescription(resultSet.getString("DESCRIPTION"));
				workListBean.setStart(resultSet.getDate("START"));
				workListBean.setEnd(resultSet.getDate("END"));
				
				list.add(workListBean);
			}
			
			responseBean.setFlag(true);
			responseBean.setTitle("success");
			responseBean.setMessage(Util.builder(year, " list called"));
			responseBean.setBody(list);
			
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
			databaseModule.close(resultSet);
			databaseModule.close(statement);
			databaseModule.close(connection);
		}
		
		return response;
	}
	
	/**
	 * 월별 리스트 응답 반환 메서드
	 *
	 * @param year: [int] 연
	 * @param month: [int] 월
	 *
	 * @return [Response] 응답 객체
	 */
	public Response getMonthListResponse(int year, int month)
	{
		Response response;
		
		ResponseBean<LinkedList<WorkListBean>> responseBean = new ResponseBean<>();
		
		DatabaseModule databaseModule = new DatabaseModule();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		// 월별 리스트 응답 생성 시도
		try
		{
			String sql = "SELECT * FROM WORKLIST WHERE YEAR(START) = ? AND MONTH(START) = ? ORDER BY START";
			
			connection = databaseModule.getConnection();
			
			statement = connection.prepareStatement(sql);
			statement.setInt(1, year);
			statement.setInt(2, month);
			
			resultSet = statement.executeQuery();
			
			LinkedList<WorkListBean> list = new LinkedList<>();
			
			while (resultSet.next())
			{
				WorkListBean workListBean = new WorkListBean();
				workListBean.setSeq(resultSet.getInt("SEQ"));
				workListBean.setType(resultSet.getString("TYPE"));
				workListBean.setWorker(resultSet.getString("WORKER"));
				workListBean.setDescription(resultSet.getString("DESCRIPTION"));
				workListBean.setStart(resultSet.getDate("START"));
				workListBean.setEnd(resultSet.getDate("END"));
				
				list.add(workListBean);
			}
			
			responseBean.setFlag(true);
			responseBean.setTitle("success");
			responseBean.setMessage(Util.builder(year, ".", month, " list called"));
			responseBean.setBody(list);
			
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
			databaseModule.close(resultSet);
			databaseModule.close(statement);
			databaseModule.close(connection);
		}
		
		return response;
	}
	
	/**
	 * 주별 리스트 응답 반환 메서드
	 *
	 * @param year: [int] 연
	 * @param week: [int] 주
	 *
	 * @return [Response] 응답 객체
	 */
	public Response getWeekListResponse(int year, int week)
	{
		Response response;
		
		ResponseBean<LinkedList<WorkListBean>> responseBean = new ResponseBean<>();
		
		DatabaseModule databaseModule = new DatabaseModule();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		// 주별 리스트 응답 생성 시도
		try
		{
			LocalDate weekMonday = Util.getFirstDayOfWeek(year, week);
			LocalDate weekFriday = weekMonday.plusDays(4);
			
			String sql = "SELECT * FROM WORKLIST WHERE START >= ? AND START <= ? ORDER BY START";
			
			connection = databaseModule.getConnection();
			
			statement = connection.prepareStatement(sql);
			statement.setDate(1, new Date(weekMonday.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()));
			statement.setDate(2, new Date(weekFriday.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()));
			
			resultSet = statement.executeQuery();
			
			LinkedList<WorkListBean> list = new LinkedList<>();
			
			while (resultSet.next())
			{
				WorkListBean workListBean = new WorkListBean();
				workListBean.setSeq(resultSet.getInt("SEQ"));
				workListBean.setType(resultSet.getString("TYPE"));
				workListBean.setWorker(resultSet.getString("WORKER"));
				workListBean.setDescription(resultSet.getString("DESCRIPTION"));
				workListBean.setStart(resultSet.getDate("START"));
				workListBean.setEnd(resultSet.getDate("END"));
				
				list.add(workListBean);
			}
			
			responseBean.setFlag(true);
			responseBean.setTitle("success");
			responseBean.setMessage(Util.builder(year, ".W", week, " list called"));
			responseBean.setBody(list);
			
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
			databaseModule.close(resultSet);
			databaseModule.close(statement);
			databaseModule.close(connection);
		}
		
		return response;
	}
}