package klid.list.bean;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

/**
 * WORKLIST 테이블 객체 클래스
 *
 * @author RWB
 * @since 2021.11.01 Mon 01:48:40
 */
@Getter
@Setter
public class WorkListBean
{
	// 시퀀스
	private long seq;
	
	// 업무 타입
	private String type;
	
	// 업무 담당자
	private String worker;
	
	// 설명
	private String description;
	
	// 시작일자
	private Date start;
	
	// 종료일자
	private Date end;
}