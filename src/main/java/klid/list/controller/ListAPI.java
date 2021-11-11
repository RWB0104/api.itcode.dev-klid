package klid.list.controller;

import global.module.API;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import klid.list.bean.WorkListBean;
import klid.list.process.ListDeleteProcess;
import klid.list.process.ListGetProcess;
import klid.list.process.ListPostProcess;
import klid.list.process.ListPutProcess;

/**
 * 리스트 API 클래스
 *
 * @author RWB
 * @since 2021.11.01 Mon 00:30:31
 */
@Path("/list")
public class ListAPI extends API
{
	/**
	 * 연별 리스트 응답 메서드
	 *
	 * @param year: [int] 연
	 *
	 * @return [Response] 응답 객체
	 */
	@GET
	@Path("/ym/{year}")
	public Response yearListResponse(@PathParam("year") int year)
	{
		return new ListGetProcess(request, response).getYearListResponse(year);
	}
	
	/**
	 * 월별 리스트 응답 메서드
	 *
	 * @param year: [int] 연
	 * @param month: [int] 월
	 *
	 * @return [Response] 응답 객체
	 */
	@GET
	@Path("/ym/{year}/{month}")
	public Response monthListResponse(@PathParam("year") int year, @PathParam("month") int month)
	{
		return new ListGetProcess(request, response).getMonthListResponse(year, month);
	}
	
	/**
	 * 주별 리스트 응답 메서드
	 *
	 * @param year: [int] 연
	 * @param week: [int] 주
	 *
	 * @return [Response] 응답 객체
	 */
	@GET
	@Path("/wk/{year}/{week}")
	public Response weekListResponse(@PathParam("year") int year, @PathParam("week") int week)
	{
		return new ListGetProcess(request, response).getWeekListResponse(year, week);
	}
	
	/**
	 * 리스트 아이템 추가 응답 메서드
	 *
	 * @param workListBean: [WorkListBean] WORKLIST 테이블 객체
	 *
	 * @return [Response] 응답 객체
	 */
	@POST
	@Path("/item")
	public Response postListItemResponse(WorkListBean workListBean)
	{
		return new ListPostProcess(request, response).postListItemResponse(workListBean);
	}
	
	/**
	 * 리스트 아이템 갱신 응답 메서드
	 *
	 * @param seq: [long] 시퀀스
	 * @param workListBean: [WorkListBean] WORKLIST 테이블 객체
	 *
	 * @return [Response] 응답 객체
	 */
	@PUT
	@Path("/item/{seq}")
	public Response putListItemResponse(@PathParam("seq") long seq, WorkListBean workListBean)
	{
		return new ListPutProcess(request, response).putListItemResponse(seq, workListBean);
	}
	
	/**
	 * 리스트 아이템 삭제 응답 메서드
	 *
	 * @param seq: [long] 시퀀스
	 *
	 * @return [Response] 응답 객체
	 */
	@DELETE
	@Path("/item/{seq}")
	public Response deleteListItemResponse(@PathParam("seq") long seq)
	{
		return new ListDeleteProcess(request, response).deleteListItemResponse(seq);
	}
}