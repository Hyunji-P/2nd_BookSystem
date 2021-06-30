package bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 회원 월별 통계 
// 2021-06-18_박현지
// 신규 통계 작업

@Getter
@Setter
@ToString
public class StatisticMonth {
	private String email; // 이메일
	private int month; // 대여 시작월 
	private int count; // 갯수
}
