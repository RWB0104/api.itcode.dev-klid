package global.bean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

/**
 * 응답 객체 클래스
 *
 * @param <T> 제네릭 클래스
 *
 * @author RWB
 * @since 2021.11.01 Mon 00:19:11
 */
@Getter
@Setter
public class ResponseBean<T>
{
	// 결과
	private boolean flag;
	
	// 제목
	private String title;
	
	// 메세지
	private String message;
	
	// 내용
	private T body;
	
	/**
	 * 객체 문자열 반환 메서드
	 *
	 * @return [String] 객체 문자열
	 */
	@Override
	public String toString()
	{
		String result;
		
		// 문자열 반환 시도
		try
		{
			result = new ObjectMapper().writeValueAsString(this);
		}
		
		// JSON 파싱 예외
		catch (JsonProcessingException e)
		{
			e.printStackTrace();
			
			result = null;
		}
		
		return result;
	}
}