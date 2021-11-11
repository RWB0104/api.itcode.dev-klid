package klid.statistics.process;

import global.bean.ResponseBean;
import global.module.DatabaseModule;
import global.module.Process;
import global.module.Util;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * 통계 GET 프로세스 클래스
 *
 * @author RWB
 * @since 2021.11.06 Sat 02:11:26
 */
public class StatisticsGetProcess extends Process
{
	/**
	 * 생성자 메서드
	 *
	 * @param request: [HttpServletRequest] HttpServletRequest 객체
	 * @param response: [HttpServletResponse] HttpServletResponse 객체
	 */
	public StatisticsGetProcess(HttpServletRequest request, HttpServletResponse response)
	{
		super(request, response);
	}
	
	/**
	 * 통계 응답 반환 메서드
	 *
	 * @param year: [int] 연
	 * @param week: [int] 주
	 *
	 * @return [Response] 응답 객체
	 */
	public Response getStatisticsResponse(int year, int week)
	{
		Response response;
		
		ResponseBean<LinkedList<LinkedHashMap<String, Object>>> responseBean = new ResponseBean<>();
		
		DatabaseModule databaseModule = new DatabaseModule();
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		// 통계 응답 생성 시도
		try
		{
			LocalDate weekFriday = Util.getFirstDayOfWeek(year, week).plusDays(4);
			
			String sql = "CALL GET_STATISTICS(?)";
			
			connection = databaseModule.getConnection();
			
			statement = connection.prepareStatement(sql);
			statement.setDate(1, new Date(weekFriday.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()));
			
			resultSet = statement.executeQuery();
			
			LinkedList<LinkedHashMap<String, Object>> list = new LinkedList<>();
			
			while (resultSet.next())
			{
				LinkedHashMap<String, Date> metaMap = new LinkedHashMap<>();
				LinkedHashMap<String, Integer> allMap = new LinkedHashMap<>();
				LinkedHashMap<String, Integer> doneMap = new LinkedHashMap<>();
				LinkedHashMap<String, Integer> failMap = new LinkedHashMap<>();
				
				metaMap.put("start_date", resultSet.getDate("START_DATE"));
				metaMap.put("end_date", resultSet.getDate("END_DATE"));
				
				allMap.put("total", resultSet.getInt("TOTAL"));
				allMap.put("monitor", resultSet.getInt("MONITOR"));
				allMap.put("kais", resultSet.getInt("KAIS"));
				allMap.put("city", resultSet.getInt("CITY"));
				allMap.put("check", resultSet.getInt("CHECK"));
				allMap.put("develope", resultSet.getInt("DEVELOPE"));
				allMap.put("educate", resultSet.getInt("EDUCATE"));
				
				doneMap.put("total", resultSet.getInt("TOTAL_W"));
				doneMap.put("monitor", resultSet.getInt("MONITOR_W"));
				doneMap.put("kais", resultSet.getInt("KAIS_W"));
				doneMap.put("city", resultSet.getInt("CITY_W"));
				doneMap.put("check", resultSet.getInt("CHECK_W"));
				doneMap.put("develope", resultSet.getInt("DEVELOPE_W"));
				doneMap.put("educate", resultSet.getInt("EDUCATE_W"));
				
				failMap.put("total", resultSet.getInt("TOTAL_F"));
				failMap.put("monitor", resultSet.getInt("MONITOR_F"));
				failMap.put("kais", resultSet.getInt("KAIS_F"));
				failMap.put("city", resultSet.getInt("CITY_F"));
				failMap.put("check", resultSet.getInt("CHECK_F"));
				failMap.put("develope", resultSet.getInt("DEVELOPE_F"));
				failMap.put("educate", resultSet.getInt("EDUCATE_F"));
				
				LinkedHashMap<String, Object> map = new LinkedHashMap<>();
				map.put("meta", metaMap);
				map.put("all", allMap);
				map.put("done", doneMap);
				map.put("fail", failMap);
				
				list.add(map);
			}
			
			responseBean.setFlag(true);
			responseBean.setTitle("success");
			responseBean.setMessage(Util.builder(year, ".W", week, " statistics called"));
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