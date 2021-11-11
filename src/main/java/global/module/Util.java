package global.module;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;
import java.util.Properties;

/**
 * 유틸 모듈 클래스
 *
 * @author RWB
 * @since 2021.11.01 Mon 00:18:09
 */
public class Util
{
	/**
	 * 텍스트 빌더 결과 반환 메서드
	 *
	 * @param objs: [Object[]] 문자열 가변 인수
	 *
	 * @return [String] 빌더 결과 문자열
	 */
	public static String builder(Object... objs)
	{
		StringBuilder builder = new StringBuilder();
		
		for (Object obj : objs)
		{
			builder.append(obj);
		}
		
		return builder.toString();
	}
	
	/**
	 * WEB-INF 경로 반환 메서드
	 *
	 * @return [String] WEB-INF 경로
	 */
	public static String getInfPath()
	{
		ClassLoader classLoader = Util.class.getClassLoader();
		
		return new File(Objects.requireNonNull(classLoader.getResource("/")).getPath()).getParent();
	}
	
	/**
	 * 프로퍼티 맵 반환 메서드
	 *
	 * @param name: [String] 프로퍼티 파일명
	 *
	 * @return [HashMap<String, String>] 프로퍼티 맵
	 *
	 * @throws IOException 파일 입출력 예외
	 */
	public static HashMap<String, String> getProperties(String name) throws IOException
	{
		String path = builder(getInfPath(), File.separator, name);
		
		// 파일명 .properties로 끝나지 않을 경우
		if (!name.endsWith(".properties"))
		{
			path = builder(path, ".properties");
		}
		
		HashMap<String, String> map = new HashMap<>();
		
		Properties properties = new Properties();
		properties.load(new FileInputStream(path));
		properties.keySet().forEach(e -> {
			String key = e.toString();
			
			map.put(key, properties.getProperty(key));
		});
		
		return map;
	}
	
	/**
	 * 주 번호의 월요일 반환 메서드
	 *
	 * @param year: [int] 연
	 * @param weekNumber: [int] 주 번호
	 *
	 * @return [LocalDate] 주 번호의 월요일
	 */
	public static LocalDate getFirstDayOfWeek(int year, int weekNumber)
	{
		return LocalDate
				.of(year, 2, 1)
				.with(WeekFields.of(Locale.UK).getFirstDayOfWeek())
				.with(WeekFields.of(Locale.UK).weekOfWeekBasedYear(), weekNumber);
	}
}