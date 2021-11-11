package global.module;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 프로세스 추상 클래스
 *
 * @author RWB
 * @since 2021.11.01 Mon 00:18:45
 */
abstract public class Process
{
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
	/**
	 * 생성자 메서드
	 *
	 * @param request: [HttpServletRequest] HttpServletResponse 객체
	 * @param response: [HttpServletResponse] HttpServletResponse 객체
	 */
	protected Process(HttpServletRequest request, HttpServletResponse response)
	{
		this.request = request;
		this.response = response;
	}
}