package bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 2021-06-22_박현지
// 회원별, 도서별 월별 통계 화면에서 상세 화면에 쓰일 bean 클래스 

@Getter
@Setter
@ToString
public class StatisticsDetail {
	private String email; // 대여자 이메일 
	private String name; // 대여자 이름
	private int bookNumber; // 대여한 책 번호
	private String bookTitle; // 대여한 책 이름 
	private String startDate; // 대여 시작일 
	private String endDate; // 대여 종료일 
}
