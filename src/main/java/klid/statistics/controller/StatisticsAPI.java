package klid.statistics.controller;

import global.module.API;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import klid.statistics.process.StatisticsGetProcess;

/**
 * 통계 API 클래스
 *
 * @author RWB
 * @since 2021.11.06 Sat 02:05:18
 */
@Path("/statistics")
public class StatisticsAPI extends API
{
	/**
	 * 통계 자료 응답 메서드
	 *
	 * @param year: [int] 연
	 * @param week: [int] 주
	 *
	 * @return [Response] 통계 자료 객체
	 */
	@GET
	@Path("/{year}/{week}")
	public Response statisticsResponse(@PathParam("year") int year, @PathParam("week") int week)
	{
		return new StatisticsGetProcess(request, response).getStatisticsResponse(year, week);
	}
}