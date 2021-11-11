package global.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 데이터베이스 객체 클래스
 *
 * @author RWB
 * @since 2021.11.01 Mon 01:21:04
 */
@Getter
@Setter
@AllArgsConstructor
public class DatabaseBean
{
	// 클래스 이름
	private String className;
	
	// 데이터베이스 URL
	private String url;
	
	// 아이디
	private String id;
	
	// 비밀번호
	private String password;
}