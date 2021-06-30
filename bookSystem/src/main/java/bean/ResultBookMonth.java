package bean;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ResultBookMonth {
	private int bookNumber; // 도서 번호
	private Map<Integer, Integer> bookMap; // 내용물
	private int total; // 총건수
}
