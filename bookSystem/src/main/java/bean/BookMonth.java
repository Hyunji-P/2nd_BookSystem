package bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 2021-06-22_박현지 
// 도서별 월별 통계 화면에 사용될 bean 클래스

@Getter
@Setter
@ToString
public class BookMonth {
	private int bookNumber; // 도서 번호 
	private int month; // 월 
	private int count; // 도서 건수 
}
