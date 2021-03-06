package global.module;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

/**
 * CORS 필터 클래스
 *
 * @author RWB
 * @since 2021.11.01 Mon 00:17:43
 */
@Provider
public class CorsFilter implements ContainerResponseFilter
{
	/**
	 * 필터 메서드
	 *
	 * @param requestContext: [ContainerRequestContext] ContainerRequestContext 객체
	 * @param responseContext: [ContainerResponseContext] ContainerResponseContext 객체
	 */
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
	{
		String origin = requestContext.getHeaderString("origin");
		
		// origin이 유효하고, itcode.dev 계열의 URL일 경우
		if (origin != null && (origin.contains("itcode.dev") || origin.contains("localhost")))
		{
			responseContext.getHeaders().add("Access-Control-Allow-Origin", origin);
			responseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type");
			responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
		}
	}
}