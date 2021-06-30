package bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class RentalBean {
	private int reSeq; // 시퀀스
	private int reBookNumber; // 대여번호
	private String reEmail; // 회원 이메일
	private String reStartDate; // 대여 시작일 = 오늘 날짜
	private String reEndDate; // 대여 종료일
	private String reRemark; // 비고
	
	
}
