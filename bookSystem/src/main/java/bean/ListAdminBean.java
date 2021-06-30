package bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 2021-06-16_박현지
// 관리자용 도서 대여 통계에 활용할 bean

@Getter
@Setter
@ToString
public class ListAdminBean {
	// 게시글 검색 시 활용할 변수
	private String email;
	private String name;
	private String phone;
	private int bookNumber;
	private int rentalNumber;
	private String startDate;
	private String endDate;
	private String remark;
}
