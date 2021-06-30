package bean;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberBean {
	
	private String meEmail; // 이메일
	
	private String meDivision; // 회원 분류
	
	private String mePassword; // 비밀 번호
	
	private String mePhone; // 휴대폰 번호
	
	private String meName; // 이름 
}
